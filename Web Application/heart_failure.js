var ageInput = document.getElementById("age");
var ageValue = ageInput.value;
var ageInputShow = document.getElementById("age_show");
ageInputShow.innerHTML = ageValue+"Years";

function ageRangeSlider(value) {
    console.log(value);
    ageInputShow.innerHTML = value + "years";
    ageValue = value;
    

}


var bpInput = document.getElementById("bp");
var bpValue = bpInput.value;
var bpInputShow = document.getElementById("bp_show");
bpInputShow.innerHTML = bpValue;

function bpRangeSlider(value) {
    console.log(value);
    bpInputShow.innerHTML = value;
    bpValue = value;

}


var cholesterolInput = document.getElementById("cholesterol");
var cholesterolValue = cholesterolInput.value;
var cholesterolInputShow = document.getElementById("cholesterol_show");
cholesterolInputShow.innerHTML = cholesterolValue;

function cholesterolRangeSlider(value) {
    console.log(value);
    cholesterolInputShow.innerHTML = value;
    cholesterolValue = value;

}

var maxhrInput = document.getElementById("maxhr");
var maxhrValue = maxhrInput.value;
var maxhrInputShow = document.getElementById("maxhr_show");
maxhrInputShow.innerHTML = maxhrValue;

function maxhrRangeSlider(value) {
    console.log(value);
    maxhrInputShow.innerHTML = value;
    maxhrValue = value;

}





var maleRadio = document.getElementById("male");
var femaleRadio = document.getElementById("female");
function getSelectedGender() {
    if (maleRadio.checked) {
        return 1;
    } else if (femaleRadio.checked) {
        return 0;
    } else {
        // If none is selected, you may handle it as needed
        return "Not Specified";
    }
}

// Accessing and using the selected gender
var selectedGender = getSelectedGender();
console.log("Selected Gender: " + selectedGender);


var chest_pain_ta_Radio = document.getElementById("ta");
var chest_pain_ata_Radio = document.getElementById("ata");
var chest_pain_nap_Radio = document.getElementById("nap");
var chest_pain_asy_Radio = document.getElementById("asy");

function getSelectedChestPain() {
    if (chest_pain_ta_Radio.checked) {
        return 3;
    } else if (chest_pain_ata_Radio.checked) {
        return 1;
    }
    else if (chest_pain_nap_Radio.checked) {
        return 2;
    }
    else if (chest_pain_asy_Radio.checked) {
        return 0;
    }
    else {
        // If none is selected, you may handle it as needed
        return "Not Specified";
    }
}
// Accessing and using the selected gender
var selectedChestPainTypelLevel = getSelectedChestPain();
console.log("Selected Cholesterol: " + selectedChestPainTypelLevel);


var fastingbs_no_Radio = document.getElementById("fs0");
var fastingbs_yes_Radio = document.getElementById("fs1");
function getSelectedfasttingbs() {
    if (fastingbs_no_Radio.checked) {
        return 0;
    } else if (fastingbs_yes_Radio.checked) {
        return 1;
    }
     else {
        // If none is selected, you may handle it as needed
        return "Not Specified";
    }
}
// Accessing and using the selected gender
var selectedFastingBSLevel = getSelectedfasttingbs();
console.log("Selected Cholesterol: " + selectedFastingBSLevel);



// Accessing radio buttons for smoking status
var ecg_LVH_Radio = document.getElementById("lvh");
var ecg_st_Radio = document.getElementById("st");
var ecg_normal_Radio = document.getElementById("normal");
// Function to get the selected smoking status
function getSelectedECG() {
    if (ecg_LVH_Radio.checked) {
        return 0;
    } else if (ecg_normal_Radio.checked) {
        return 1;
    } 
    else if (ecg_st_Radio.checked) {
        return 2;
    }
    else {
        // If none is selected, you may handle it as needed
        return "Not Specified";
    }
}
var selectedECGType = getSelectedECG();
console.log("Selected Smoking Status: " + selectedECGType);


var eiaNoRadio = document.getElementById("exercise_angina_no");
var eiaYesRadio = document.getElementById("exercise_angina_yes");
// Function to get the selected alcohol status
function getSelectedEIAStatus() {
    if (eiaNoRadio.checked) {
        return 0;
    } else if (eiaYesRadio.checked) {
        return 1;
    } else {
        // If none is selected, you may handle it as needed
        return "Not Specified";
    }
}
var selectedEIAStatus = getSelectedEIAStatus();
console.log("Selected Alcohol Status: " + selectedEIAStatus);


function predict() {
    console.log("Clicked")
    // Assuming you have the input values available
    var input_data_for_model = {
        'Age': ageValue,
        'Gender': getSelectedGender(),
        'ChestPain': getSelectedChestPain(),
        'BP': bpValue,
        'Cholesterol': cholesterolValue,
        'FBS': getSelectedfasttingbs(),
        'ECG': getSelectedECG(),
        'MHR': maxhrValue,
        'EIA': getSelectedEIAStatus()
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
    var url = 'http://127.0.0.1:8000/heart_failure';

    // Making the API call
    fetch(url, fetchOptions)
        .then(response => response.json())
        .then(data => {

            console.log(data); // Handle the response data as needed

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