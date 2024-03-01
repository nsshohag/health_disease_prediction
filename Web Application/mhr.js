var ageInput = document.getElementById("age");
var ageValue = ageInput.value;
var ageInputShow = document.getElementById("age_show");
ageInputShow.innerHTML = ageValue+"Years";

function ageRangeSlider(value) {
    console.log(value);
    ageInputShow.innerHTML = value + "years";
    ageValue = value;
    

}

var sys_pressureInput = document.getElementById("sys_pressure");
var sys_pressureValue = sys_pressureInput.value;
var sys_pressureInputShow = document.getElementById("sys_p_show");
sys_pressureInputShow.innerHTML = sys_pressureValue;

function sys_pressureRangeSlider(value) {
    console.log(value);
    sys_pressureInputShow.innerHTML = value;
    sys_pressureValue = value;

}

var dia_pressureInput = document.getElementById("dia_pressure");
var dia_pressureValue = dia_pressureInput.value;
var dia_pressureInputShow = document.getElementById("dia_p_show");
dia_pressureInputShow.innerHTML = dia_pressureValue;

function dia_pressureRangeSlider(value) {
    console.log(value);
    dia_pressureInputShow.innerHTML = value;
    dia_pressureValue = value;

}

var bsInput = document.getElementById("bs");
var bsValue = bsInput.value;
var bsInputShow = document.getElementById("bs_show");
bsInputShow.innerHTML = bsValue;

function bsRangeSlider(value) {
    console.log(value);
    bsInputShow.innerHTML = value;
    bsValue = value;

}

var btInput = document.getElementById("bt");
var btValue = btInput.value;
var btInputShow = document.getElementById("bt_show");
btInputShow.innerHTML = btValue;

function btRangeSlider(value) {
    console.log(value);
    btInputShow.innerHTML = value;
    btValue = value;

}

var hrInput = document.getElementById("hr");
var hrValue = hrInput.value;
var hrInputShow = document.getElementById("hr_show");
hrInputShow.innerHTML = hrValue;

function hrRangeSlider(value) {
    console.log(value);
    hrInputShow.innerHTML = value;
    hrValue = value;

}



function predict() {

    console.log("Clicked")
    // Assuming you have the input values available
    var input_data_for_model = {
        'Age': ageValue,
        'SystolicBP': sys_pressureValue,
        'DiastolicBP': dia_pressureValue,
        'BS': bsValue,
        'BodyTemp': btValue,
        'HeartRate': hrValue
    };


    console.log(input_data_for_model);

    // Fetch options
    var fetchOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(input_data_for_model),
    };

    // Replace 'http://127.0.0.1:8000/mhr' with your actual API endpoint
    var url = 'http://127.0.0.1:8000/mhr';

    // Making the API call
    fetch(url, fetchOptions)
        .then(response => response.json())
        .then(data => {

            console.log(data); // Handle the response data as needed

            if(data=="high risk"){
                
                console.log("high risk");

                var lottieContainer = document.getElementById("sadaaa");
                lottieContainer.innerHTML = ''; // or lottieContainer.remove(); // remove() use korle age kichu thakte hobe tarpor delete korte hobe
                $("#result_warning").html("Hey there! You're at HIGH RISK.").css("color", "red");

                bodymovin.loadAnimation({
                    container: document.getElementById("sadaaa"),
                    path: 'https://lottie.host/7a34cb19-2923-4dbb-9b3b-1cade2958cae/KDkOW6MjUr.json'

                })             

            }
            else if(data=="mid risk")
            {
                $("#result_warning").html("Hey there! You're at MID RISK.").css("color", "#eb8181");
                var lottieContainer = document.getElementById("sadaaa");
                lottieContainer.innerHTML = ''; // or lottieContainer.remove();
                bodymovin.loadAnimation({
                    container: document.getElementById("sadaaa"),
                    path: 'https://lottie.host/88e93e09-47ba-4c44-a91e-8ad3afefed9a/ZHHyLQ57NO.json'

                })
            }

            else{

                var lottieContainer = document.getElementById("sadaaa");
                lottieContainer.innerHTML = ''; // or lottieContainer.remove(); // remove() use korle age kichu thakte hobe tarpor delete korte hobe
                $("#result_warning").html("Hey there! You're are at LOW RISK.").css("color", "green");
                bodymovin.loadAnimation({
                    container: document.getElementById("sadaaa"),
                    path: 'https://lottie.host/0b629822-5657-463c-accd-6751aac2d2d0/8Jb3t1e0eO.json'

                })

            }


            $("#exampleModalLong").modal("show");

        })
        .catch(error => {
            console.error('Error:', error);
        });



}