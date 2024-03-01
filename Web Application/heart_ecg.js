Dropzone.autoDiscover = false;

function init() {

    let dz = new Dropzone("#dropzone", {
        url: "http://127.0.0.1:8000/heart_ecg",
        maxFiles: 1,
        addRemoveLinks: true,
        dictDefaultMessage: "Some Message",
        autoProcessQueue: false,
        paramName: "image"
    });


    dz.on("addedfile", function () {
        if (dz.files[1] != null) {
            dz.removeFile(dz.files[0]);
        }
    });



    dz.on("complete", function (file) {

        //let imageData = file.dataURL;

        var url = "http://127.0.0.1:8000/heart_ecg";

        let formData = new FormData();
        formData.append("image", file);

        $.ajax({ // ajax functionality.
            type: "POST", // type post
            url: url,// provided the FastAPI endpoint url
            data: formData, // data or image sent
            processData: false,
            contentType: false,
            success: function (data, status) {
                // Handle the response from the FastAPI server
                console.log(data);
                // Rest of your code for handling the response


                if (!data || data.length == 0) {
                    $("#resultHolder").hide();
                    $("#divClassTable").hide();
                    $("#error").show();
                    return;
                }

                let maxProbability = -Infinity;
                let maxDisease = '';
                for (const [disease, probability] of Object.entries(data)) {
                    if (probability > maxProbability) {
                        maxProbability = probability;
                        maxDisease = disease;
                    }
                }

                console.log('Max Disease:', maxDisease);
                console.log('Max Probability:', maxProbability);


                if (data.length != 0) {
                    $("#error").hide();
                    $("#resultHolder").show();
                    $("#divClassTable").show();

                    $("#resultHolder").html($(`[data-player="${maxDisease}"`).html());

                    const normalProbability = (data['Normal']*100).toFixed(2);
                    const abnormalProbability = (data['Abnormal']*100).toFixed(2);

                    $("#score_normal").html(normalProbability);
                    $("#score_abnormal").html(abnormalProbability);
                    
                    

                    var normal_val = normalProbability;
                    var abnormal_val = abnormalProbability;

                    var highestProbability = Math.max(normal_val, abnormal_val);
                    console.log(highestProbability);


                    if (highestProbability<80) {
                        console.log("Could not detect");

                        $("#resultHolder").hide();
                        $("#divClassTable").hide();
                        $("#result_warning").html("Sorry Couldn't Classify ! You May Have Provided Wrong Image").css("color", "red");
                        var lottieContainer = document.getElementById("sadaaa");
                        lottieContainer.innerHTML = ''; // or lottieContainer.remove();
                        bodymovin.loadAnimation({
                            container: document.getElementById("sadaaa"),
                            path: 'https://lottie.host/422d30bb-7a5f-4486-98e3-393a33f35553/2lmMy5Fdfc.json'

                        })


                    }

                    else {
                        
                        $("#resultHolder").show();
                        $("#divClassTable").show();

                        if (maxDisease=='Normal') {

                            var lottieContainer = document.getElementById("sadaaa");
                            lottieContainer.innerHTML = ''; // or lottieContainer.remove(); // remove() use korle age kichu thakte hobe tarpor delete korte hobe
                            $("#result_warning").html("Hey there! Your heart ECG is NORMAL.").css("color", "green");
                            bodymovin.loadAnimation({
                                container: document.getElementById("sadaaa"),
                                path: 'https://lottie.host/0b629822-5657-463c-accd-6751aac2d2d0/8Jb3t1e0eO.json'

                            })

                        }
                        else {

                            
                            $("#result_warning").html("Hey there! Your heart ECG is ABNORMAL.").css("color", "red");
                            var lottieContainer = document.getElementById("sadaaa");
                            lottieContainer.innerHTML = ''; // or lottieContainer.remove();
                            bodymovin.loadAnimation({
                                container: document.getElementById("sadaaa"),
                                path: 'https://lottie.host/88e93e09-47ba-4c44-a91e-8ad3afefed9a/ZHHyLQ57NO.json'

                            })

                        }

                    }

                    $("#exampleModalLong").modal("show");

                    $("#myLottiePlayer").attr("src", "https://lottie.host/88e93e09-47ba-4c44-a91e-8ad3afefed9a/ZHHyLQ57NO.json");


                }


            },
            error: function (xhr, status, error) {
                // Handle the error if the request fails
                console.error(error);
            },
        });

        // $("#sadaaa").html("KILL me .");




    });

    $("#submitBtn").on('click', function (e) {

        dz.processQueue();

    });
}


$(document).ready(function () {
    console.log("ready!");
    $("#error").hide();
    $("#resultHolder").hide();
    $("#divClassTable").hide();

    init();
});
