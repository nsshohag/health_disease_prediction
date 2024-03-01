Dropzone.autoDiscover = false;

function init() {

    let dz = new Dropzone("#dropzone", {
        url: "http://127.0.0.1:8000/skin_diseases",
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

        var url = "http://127.0.0.1:8000/skin_diseases";

        let formData = new FormData();
        formData.append("image", file);

        $.ajax({
            type: "POST",
            url: url,
            data: formData,
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

                    const acneProbability = (data['Acne']*100).toFixed(2);
                    const eczemaProbability = (data['Eczema']*100).toFixed(2);
                    const melanomaProbability = (data['Melanoma']*100).toFixed(2);
                    const stdsProbability = (data['STDs']*100).toFixed(2);

                    $("#score_acne").html(acneProbability);
                    $("#score_eczema").html(eczemaProbability);
                    $("#score_melanoma").html(melanomaProbability);
                    $("#score_stds").html(stdsProbability);
                    
                    

                    var acne_val = acneProbability;
                    var eczema_val = eczemaProbability;
                    var melanoma_val = melanomaProbability;
                    var stds_val = stdsProbability;

                    var highestProbability = Math.max(acne_val, eczema_val, melanoma_val, stds_val);
                    console.log(highestProbability);


                    if (highestProbability<70) {
                        $("#resultHolder").hide();
                        $("#divClassTable").hide();
                        $("#result_warning").html("Sorry ! Couldn't Classify.").css("color", "red");
                        var lottieContainer = document.getElementById("sadaaa");
                        lottieContainer.innerHTML = ''; // or lottieContainer.remove();
                        bodymovin.loadAnimation({
                            container: document.getElementById("sadaaa"),
                            path: 'https://lottie.host/422d30bb-7a5f-4486-98e3-393a33f35553/2lmMy5Fdfc.json'

                        });

                    }

                    else {

                        $("#resultHolder").show();
                        $("#divClassTable").show();

                        if (maxDisease=='Acne') {
                            $("#result_warning").html("Hey there! You may have ACNE.").css("color", "red");
                            var lottieContainer = document.getElementById("sadaaa");
                            lottieContainer.innerHTML =''; // or lottieContainer.remove(); // remove() use korle age kichu thakte hobe tarpor delete korte hobe
                            bodymovin.loadAnimation({
                                container: document.getElementById("sadaaa"),
                                path: 'https://lottie.host/b6f79c52-c866-4be8-8cae-8bb6e67a24b3/39PYhDUUKg.json'

                            })

                        }

                        else if(maxDisease=='Eczema'){

                            $("#result_warning").html("Hey there! You may have ECZEMA.").css("color", "red");
                            var lottieContainer = document.getElementById("sadaaa");
                            lottieContainer.innerHTML =''; // or lottieContainer.remove(); // remove() use korle age kichu thakte hobe tarpor delete korte hobe
                            
                            bodymovin.loadAnimation({
                                container: document.getElementById("sadaaa"),
                                path: 'https://lottie.host/88e93e09-47ba-4c44-a91e-8ad3afefed9a/ZHHyLQ57NO.json'
                            })

                        }

                        else if(maxDisease=='Melanoma'){

                            $("#result_warning").html("Hey there! You may have MELANOMA.").css("color", "red");
                            var lottieContainer = document.getElementById("sadaaa");
                            lottieContainer.innerHTML =''; // or lottieContainer.remove(); // remove() use korle age kichu thakte hobe tarpor delete korte hobe
                            
                            bodymovin.loadAnimation({
                                container: document.getElementById("sadaaa"),
                                path: 'https://lottie.host/88e93e09-47ba-4c44-a91e-8ad3afefed9a/ZHHyLQ57NO.json'
                            })


                        }

                        else {

                            $("#result_warning").html("Hey there! You may have STDs.").css("color", "red");
                            var lottieContainer = document.getElementById("sadaaa");
                            lottieContainer.innerHTML =''; // or lottieContainer.remove(); // remove() use korle age kichu thakte hobe tarpor delete korte hobe
                            
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
