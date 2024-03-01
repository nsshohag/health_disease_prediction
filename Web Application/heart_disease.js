var ageInput = document.getElementById("age");
var ageValue = ageInput.value;
var ageInputShow = document.getElementById("age_show");
ageInputShow.innerHTML = ageValue+"Years";

function ageRangeSlider(value) {
    console.log(value);
    ageInputShow.innerHTML = value + "years";
    ageValue = value;
    

}


var heightInput = document.getElementById("height");
var heightValue = heightInput.value;
var heightInputShow = document.getElementById("height_show");
heightInputShow.innerHTML = heightValue+"cm";

function heightRangeSlider(value) {
    console.log(value);
    heightInputShow.innerHTML = value+"cm";
    heightValue = value;

}


var weightInput = document.getElementById("weight");
var weightValue = weightInput.value;
var weightInputShow = document.getElementById("weight_show");
weightInputShow.innerHTML = weightValue;

function weightRangeSlider(value) {
    console.log(value);
    weightInputShow.innerHTML = value;
    weightValue = value;

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



var maleRadio = document.getElementById("male");
var femaleRadio = document.getElementById("female");
function getSelectedGender() {
    if (maleRadio.checked) {
        return 2;
    } else if (femaleRadio.checked) {
        return 1;
    } else {
        // If none is selected, you may handle it as needed
        return "Not Specified";
    }
}
// Accessing and using the selected gender
var selectedGender = getSelectedGender();
console.log("Selected Gender: " + selectedGender);


var c_low_Radio = document.getElementById("cholesterol_low");
var c_medium_Radio = document.getElementById("cholesterol_medium");
var c_high_Radio = document.getElementById("cholesterol_high");
function getSelectedCholesterol() {
    if (c_low_Radio.checked) {
        return 1;
    } else if (c_high_Radio.checked) {
        return 3;
    }
    else if (c_medium_Radio.checked) {
        return 2;
    } else {
        // If none is selected, you may handle it as needed
        return "Not Specified";
    }
}
// Accessing and using the selected gender
var selectedCholesterolLevel = getSelectedCholesterol();
console.log("Selected Cholesterol: " + selectedCholesterolLevel);


var gluc_low_Radio = document.getElementById("glucose_low");
var gluc_medium_Radio = document.getElementById("glucose_medium");
var gluc_high_Radio = document.getElementById("glucose_high");
function getSelectedGlucose() {
    if (gluc_low_Radio.checked) {
        return 1;
    } else if (gluc_high_Radio.checked) {
        return 3;
    }
    else if (gluc_medium_Radio.checked) {
        return 2;
    } else {
        // If none is selected, you may handle it as needed
        return "Not Specified";
    }
}
// Accessing and using the selected gender
var selectedGlucoseLevel = getSelectedGlucose();
console.log("Selected Cholesterol: " + selectedGlucoseLevel);



// Accessing radio buttons for smoking status
var smokeNoRadio = document.getElementById("smoke_no");
var smokeYesRadio = document.getElementById("smoke_yes");
// Function to get the selected smoking status
function getSelectedSmokingStatus() {
    if (smokeNoRadio.checked) {
        return 0;
    } else if (smokeYesRadio.checked) {
        return 1;
    } else {
        // If none is selected, you may handle it as needed
        return "Not Specified";
    }
}
var selectedSmokingStatus = getSelectedSmokingStatus();
console.log("Selected Smoking Status: " + selectedSmokingStatus);


var alcoholNoRadio = document.getElementById("alcohol_no");
var alcoholYesRadio = document.getElementById("alcohol_yes");
// Function to get the selected alcohol status
function getSelectedAlcoholStatus() {
    if (alcoholNoRadio.checked) {
        return 0;
    } else if (alcoholYesRadio.checked) {
        return 1;
    } else {
        // If none is selected, you may handle it as needed
        return "Not Specified";
    }
}
var selectedAlcoholStatus = getSelectedAlcoholStatus();
console.log("Selected Alcohol Status: " + selectedAlcoholStatus);


// Accessing radio buttons for active status
var activeNoRadio = document.getElementById("active_no");
var activeYesRadio = document.getElementById("active_yes");
// Function to get the selected active status
function getSelectedActiveStatus() {
    if (activeNoRadio.checked) {
        return 0;
    } else if (activeYesRadio.checked) {
        return 1;
    } else {
        // If none is selected, you may handle it as needed
        return "Not Specified";
    }
}
// Accessing and using the selected active status
var selectedActiveStatus = getSelectedActiveStatus();
console.log("Selected Active Status: " + selectedActiveStatus);


console.log(ageValue);


function predict() {
    console.log("Clicked")
    // Assuming you have the input values available
    var input_data_for_model = {
        'Age': ageValue * 365,
        'Gender': getSelectedGender(),
        'Height': heightValue,
        'Weight': weightValue,
        'Sys_BloodPressure': sys_pressureValue,
        'Dia_BloodPressure': dia_pressureValue,
        'Cholesterol': getSelectedCholesterol(),
        'Glucose': getSelectedGlucose(),
        'Smoke': getSelectedSmokingStatus(),
        'Alcohol': getSelectedAlcoholStatus(),
        'Active': getSelectedActiveStatus()
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
    var url = 'http://127.0.0.1:8000/heart_disease';

    // Making the API call
    fetch(url, fetchOptions)
        .then(response => response.json())
        .then(data => {

            console.log(data);// Handle the response data as needed

            if(data!="Risk"){

                var lottieContainer = document.getElementById("sadaaa");
                lottieContainer.innerHTML = ''; // or lottieContainer.remove(); // remove() use korle age kichu thakte hobe tarpor delete korte hobe
                $("#result_warning").html("Hey there! You're not at risk.").css("color", "green");
                bodymovin.loadAnimation({
                    container: document.getElementById("sadaaa"),
                    path: 'https://lottie.host/0b629822-5657-463c-accd-6751aac2d2d0/8Jb3t1e0eO.json'

                })

            }
            else
            {
                $("#result_warning").html("Hey there! You're at RISK.").css("color", "red");
                var lottieContainer = document.getElementById("sadaaa");
                lottieContainer.innerHTML = ''; // or lottieContainer.remove();
                bodymovin.loadAnimation({
                    container: document.getElementById("sadaaa"),
                    path: 'https://lottie.host/88e93e09-47ba-4c44-a91e-8ad3afefed9a/ZHHyLQ57NO.json'

                })

            }


            $("#exampleModalLong").modal("show");

        })
        .catch(error => {
            console.error('Error:', error);
        });
        

}