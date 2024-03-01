var ageInput = document.getElementById("age");
var ageValue = ageInput.value;
var ageInputShow = document.getElementById("age_show");
ageInputShow.innerHTML = ageValue+"Years";

function ageRangeSlider(value) {
    console.log(value);
    ageInputShow.innerHTML = value + "years";
    ageValue = value;
    

}


var pregInput = document.getElementById("preg");
var pregValue = pregInput.value;
var pregInputShow = document.getElementById("preg_show");
pregInputShow.innerHTML = pregValue+"Times";

function pregRangeSlider(value) {
    console.log(value);
    pregInputShow.innerHTML = value + "Times";
    pregValue = value;

}

var glucoseInput = document.getElementById("glucose");
var glucoseValue = glucoseInput.value;
var glucoseInputShow = document.getElementById("glucose_show");
glucoseInputShow.innerHTML = glucoseValue;

function glucoseRangeSlider(value) {
    console.log(value);
    glucoseInputShow.innerHTML = value;
    glucoseValue = value;

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

var trInput = document.getElementById("tr");
var trValue = trInput.value;
var trInputShow = document.getElementById("tr_show");
trInputShow.innerHTML = trValue;

function trRangeSlider(value) {
    console.log(value);
    trInputShow.innerHTML = value;
    trValue = value;

}

var insulinInput = document.getElementById("insulin");
var insulinValue =insulinInput.value;
var insulinInputShow = document.getElementById("insulin_show");
insulinInputShow.innerHTML = insulinValue;

function insulinRangeSlider(value) {
    console.log(value);
    insulinInputShow.innerHTML = value;
    insulinValue = value;

}

var bmiInput = document.getElementById("bmi");
var bmiValue =bmiInput.value;
var bmiInputShow = document.getElementById("bmi_show");
bmiInputShow.innerHTML = bmiValue;

function bmiRangeSlider(value) {
    console.log(value);
    bmiInputShow.innerHTML = value;
    bmiValue = value;

}


var dpfInput = document.getElementById("dpf");
var dpfValue =dpfInput.value;
var dpfInputShow = document.getElementById("dpf_show");
dpfInputShow.innerHTML = dpfValue;
function dpfRangeSlider(value) {
    console.log(value);
    dpfInputShow.innerHTML = value;
    dpfValue = value;

}




function predict() {

    console.log("Clicked")
    // Assuming you have the input values available
    var input_data_for_model = {
        'Preg': pregValue,
        'Glucose': glucoseValue,
        'Dia_BloodPressure': dia_pressureValue,
        'TSFT': trValue,
        'Insulin': insulinValue,
        'BMI': bmiValue,
        'DPF': dpfValue,
        'Age': ageValue
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

    // Replace 'http://127.0.0.1:8000/heart_disease' with your actual API endpoint
    var url = 'http://127.0.0.1:8000/diabetes';

    // Making the API call
    fetch(url, fetchOptions)
        .then(response => response.json())
        .then(data => {

            console.log(data); // Handle the response data as needed

            if(data=="Risk"){

                var lottieContainer = document.getElementById("sadaaa");
                lottieContainer.innerHTML = ''; // or lottieContainer.remove(); // remove() use korle age kichu thakte hobe tarpor delete korte hobe
                $("#result_warning").html("Hey there! You're at RISK.").css("color", "red");
                bodymovin.loadAnimation({
                    container: document.getElementById("sadaaa"),
                    path: 'https://lottie.host/88e93e09-47ba-4c44-a91e-8ad3afefed9a/ZHHyLQ57NO.json'

                })

            }
            else
            {
                var lottieContainer = document.getElementById("sadaaa");
                lottieContainer.innerHTML = ''; // or lottieContainer.remove(); // remove() use korle age kichu thakte hobe tarpor delete korte hobe
                $("#result_warning").html("Hey there! You're not at RISK.").css("color", "green");
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