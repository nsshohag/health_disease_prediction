Dropzone.autoDiscover = false;

function init() {

    let dz = new Dropzone("#dropzone", {
        url: "http://127.0.0.1:8000/pneumonia",
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

        var url = "http://127.0.0.1:8000/pneumonia";

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

                let match = null;
                let bestScore = -1;
                for (let i = 0; i < data.length; ++i) {
                    let maxScoreForThisClass = Math.max(...data[i].class_probability);
                    if (maxScoreForThisClass > bestScore) {
                        match = data[i];
                        bestScore = maxScoreForThisClass;
                    }
                }

                console.log(match.class);

                if (match) {
                    $("#error").hide();
                    $("#resultHolder").show();
                    $("#divClassTable").show();
                    $("#resultHolder").html($(`[data-player="${match.class}"`).html());

                    let k_score = data[0].class_probability[0].toFixed(2);
                    let m_score = data[0].class_probability[1].toFixed(2);
                    $("#score_kristen").html(k_score);
                    $("#score_maria").html(m_score);



                    var normal_val = k_score;
                    var pneumonia_val = m_score;
                    let maxNumber = Math.max(normal_val, pneumonia_val);
                    console.log(maxNumber);

                    if (maxNumber < 82) {

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
                        if (normal_val >= 82) {

                            var lottieContainer = document.getElementById("sadaaa");
                            lottieContainer.innerHTML = ''; // or lottieContainer.remove(); // remove() use korle age kichu thakte hobe tarpor delete korte hobe
                            $("#result_warning").html("Hey there! You're not at risk.").css("color", "green");
                            bodymovin.loadAnimation({
                                container: document.getElementById("sadaaa"),
                                path: 'https://lottie.host/0b629822-5657-463c-accd-6751aac2d2d0/8Jb3t1e0eO.json'

                            })

                        }
                        else {

                            
                            $("#result_warning").html("Hey there! You're at risk of having pneumonia.").css("color", "red");
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

                    /*
                    // Assuming you have an element with id 'lottiePlayer'
                    var lottiePlayer = document.getElementById('myLottiePlayer');

                    // Set the source URL dynamically
                    var animationSrc = sad; // Replace with your actual Lottie animation file URL
                    lottiePlayer.setAttribute('src', animationSrc);
                    */


                }


            },
            error: function (xhr, status, error) {
                // Handle the error if the request fails
                console.error(error);
            },
        });






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


/*
 
        $.post(url, {
    
    image_data: file.dataURL
},function(data, status) {

    console.log(data);

    if (!data || data.length==0) {
        $("#resultHolder").hide();
        $("#divClassTable").hide();                
        $("#error").show();
        return;
    }
    
    let match = null;
    let bestScore = -1;
    for (let i=0;i<data.length;++i) {
        let maxScoreForThisClass = Math.max(...data[i].class_probability);
        if(maxScoreForThisClass>bestScore) {
            match = data[i];
            bestScore = maxScoreForThisClass;
        }
    }
    if (match) {
        $("#error").hide();
        $("#resultHolder").show();
        $("#divClassTable").show();
        $("#resultHolder").html($(`[data-player="${match.class}"`).html());


        let k_score = data[0].class_probability[0].toFixed(2);
        let m_score = data[0].class_probability[1].toFixed(2);
        $("#score_kristen").html(k_score);
        $("#score_maria").html(m_score);

    }        
});
 
 
 
*/