import json
import requests

url = 'http://127.0.0.1:8000/heart_disease'

input_data_for_model = {
    'Age': 183932,
    'Gender': 2,
    'Height': 168,
    'Weight': 62.0,
    'Sys_BloodPressure': 110,
    'Dia_BloodPressure': 80,
    'Cholesterol': 1,
    'Glucose': 1,
    'Smoke': 0,
    'Alcohol': 0,
    'Active': 1
}

input_json = json.dumps(input_data_for_model)

response = requests.post(url, data=input_json)

print(response.text)