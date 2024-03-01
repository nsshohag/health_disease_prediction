import os

import tensorflow as tf
import tensorflow_hub as hub
import cv2
import numpy as np
import uvicorn
from fastapi import FastAPI, UploadFile
from starlette.middleware.cors import CORSMiddleware
from pydantic import BaseModel
import json
import pickle

from starlette.responses import JSONResponse

reloaded = tf.keras.models.load_model('path/1691775718_pneumonia', custom_objects={'KerasLayer':hub.KerasLayer})
reloaded_skin = tf.keras.models.load_model('path/1691918657_skin', custom_objects={'KerasLayer':hub.KerasLayer})
reloaded_heart_ecg = tf.keras.models.load_model('path/1708365270_heart_ecg', custom_objects={'KerasLayer':hub.KerasLayer})
Labels = ['NORMAL', 'PNEUMONIA']

Labels_Skin = ['Acne and Rosacea',
 'Eczema Photos',
 'Herpes HPV and other STDs Photos',
 'Melanoma']


app = FastAPI()


origins = ["*"]

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials = True,
    allow_methods=["*"],
    allow_headers=["*"],

)


@app.post("/pneumonia")
async def pneumonia(image: UploadFile):
    with open("uploaded_image/uploaded_image.jpg", "wb") as f:
        f.write(image.file.read())

    def upload(filename):
        img = cv2.imread(os.path.join("", filename))
        img = cv2.resize(img, (224, 224))
        img = img / 255
        return img

    def pre_result(image):
        x = reloaded.predict(np.asarray([image]))[0]
        classx = np.argmax(x)
        return {Labels[classx]: x[classx]}

    img = upload("uploaded_image/uploaded_image.jpg")

    prediction = pre_result(img)

    result = []

    result.append({
        'class': list(prediction.keys())[0],
        'class_probability': (reloaded.predict(np.asarray([img]))*100).tolist()[0]
    })
    print(result)

    # response = jsonify(result) eita jhamale kore
    response = JSONResponse(content=result)

    return response


@app.post("/skin_diseases")
async def skin_diseases(image: UploadFile):
    with open("uploaded_image_skin/uploaded_image_skin.jpg", "wb") as f:
        f.write(image.file.read())

    def upload(filename):
        img = cv2.imread(os.path.join("", filename))
        img = cv2.resize(img, (224, 224))
        img = img / 255
        return img

    def pre_result(image):
        x = reloaded_skin.predict(np.asarray([image]))[0]

        return x # Returning class index and probability score

    img = upload("uploaded_image_skin/uploaded_image_skin.jpg")


    y = pre_result(img)

    x = y.tolist()

    result = {
        'Acne': x[0],
        'Eczema': x[1],
        'STDs': x[2],
        'Melanoma': x[3]
    }

    print(result)

    # response = jsonify(result) eita jhamale kore
    response = JSONResponse(content=result)

    return response


@app.post("/heart_ecg")
async def heart_ecg(image: UploadFile):
    with open("uploaded_image_ecg/uploaded_image_ecg.jpg", "wb") as f:
        f.write(image.file.read())

    def upload(filename):
        img = cv2.imread(os.path.join("", filename))
        img = cv2.resize(img, (224, 224))
        img = img / 255
        return img

    def pre_result(image):
        x = reloaded_heart_ecg.predict(np.asarray([image]))[0]

        return x # Returning class index and probability score
        

    img = upload("uploaded_image_ecg/uploaded_image_ecg.jpg")

    y = pre_result(img)

    x = y.tolist()

    result = {
        'Abnormal': x[0],
        'Normal': x[1]
    }

    print(result)

    # response = jsonify(result) eita jhamale kore
    response = JSONResponse(content=result)

    return response




class model_input(BaseModel):

    Age : int
    Gender : int
    Height : int
    Weight : int
    Sys_BloodPressure : int
    Dia_BloodPressure: int
    Cholesterol : int
    Glucose : int
    Smoke : int
    Alcohol : int
    Active : int

# loading the saved model
heart_disease_model = pickle.load(open('heart_disease/heart_disease_model.sav','rb'))


@app.post('/heart_disease')
def heart_disease(input_parameters : model_input):

    input_data = input_parameters.json()
    input_dictionary = json.loads(input_data)

    Age = input_dictionary['Age']
    Gender = input_dictionary['Gender']
    Height = input_dictionary['Height']
    Weight = input_dictionary['Weight']
    Sys_BloodPressure = input_dictionary['Sys_BloodPressure']
    Dia_BloodPressure = input_dictionary['Dia_BloodPressure']
    Cholesterol = input_dictionary['Cholesterol']
    Glucose = input_dictionary['Glucose']
    Smoke = input_dictionary['Smoke']
    Alcohol = input_dictionary['Alcohol']
    Active = input_dictionary['Active']

    input_list = [Age, Gender, Height, Weight, Sys_BloodPressure, Dia_BloodPressure, Cholesterol, Glucose, Smoke ,Alcohol, Active]

    prediction = heart_disease_model.predict([input_list])

    if prediction[0] == 0:
        return 'Risk Free'
    else:
        return 'Risk'
    


class model_input_heart_failure(BaseModel):
        
        Age: int
        Gender: int
        ChestPain: int
        BP: int
        Cholesterol: int
        FBS: int
        ECG: int
        MHR: int
        EIA: int

# loading the saved model
heart_failure_model = pickle.load(open('heart_failure/heart_failure_model.sav','rb'))

@app.post('/heart_failure')
def heart_failure(input_parameters : model_input_heart_failure):

    input_data = input_parameters.json()
    input_dictionary = json.loads(input_data)

    Age = input_dictionary['Age']
    Gender = input_dictionary['Gender']
    ChestPain = input_dictionary['ChestPain']
    BP = input_dictionary['BP']
    Cholesterol = input_dictionary['Cholesterol']
    FBS = input_dictionary['FBS']
    ECG = input_dictionary['ECG']
    MHR = input_dictionary['MHR']
    EIA = input_dictionary['EIA']

    input_list = [Age, Gender, ChestPain, BP, Cholesterol, FBS, ECG, MHR, EIA]
    print(input_list)

    prediction = heart_failure_model.predict([input_list])

    if prediction[0] == 0:
        return 'Risk Free'
    else:
        return 'Risk'



class model_input_diabetes(BaseModel):
        
        Preg: int
        Glucose: int
        Dia_BloodPressure: int
        TSFT: int
        Insulin: int
        BMI: int
        DPF: float
        Age: int

# loading the saved model
diabetes_model = pickle.load(open('diabetes/diabetes_model.sav','rb'))

@app.post('/diabetes')
def diabetes(input_parameters : model_input_diabetes):

    input_data = input_parameters.json()
    input_dictionary = json.loads(input_data)

    
    Preg = input_dictionary['Preg']
    Glucose = input_dictionary['Glucose']
    Dia_BloodPressure = input_dictionary['Dia_BloodPressure']
    TSFT = input_dictionary['TSFT']
    Insulin = input_dictionary['Insulin']
    BMI = input_dictionary['BMI']
    DPF = input_dictionary['DPF']
    Age = input_dictionary['Age']

    input_list = [Preg, Glucose, Dia_BloodPressure, TSFT, Insulin, BMI, DPF, Age]
    print(input_list)

    prediction = diabetes_model.predict([input_list])

    if prediction[0] == 0:
        return 'Risk Free'
    else:
        return 'Risk'  


class model_input_mhr(BaseModel):
        
        Age: int
        SystolicBP: int
        DiastolicBP: int
        BS: int
        BodyTemp: int
        HeartRate: int

# loading the saved model
mhr_model = pickle.load(open('mhr/mhr.sav','rb'))

@app.post('/mhr')
def mhr(input_parameters : model_input_mhr):

    input_data = input_parameters.json()
    input_dictionary = json.loads(input_data)

    
    Age = input_dictionary['Age']
    SystolicBP = input_dictionary['SystolicBP']
    DiastolicBP = input_dictionary['DiastolicBP']
    BS = input_dictionary['BS']
    BodyTemp = input_dictionary['BodyTemp']
    HeartRate = input_dictionary['HeartRate']

    input_list = [Age, SystolicBP, DiastolicBP, BS, BodyTemp, HeartRate]

    print(input_list)

    prediction = mhr_model.predict([input_list])

    print(prediction)
    print(type(prediction))

    if prediction[0] == 'high risk':
        return 'high risk'
    elif prediction[0] == 'mid risk':
        return 'mid risk'
    else:
        return 'low risk'

# uvicorn.run(app, host="127.0.0.1", port=8000)
