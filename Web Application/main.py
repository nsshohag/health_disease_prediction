import base64

import tensorflow as tf
import tensorflow_hub as hub
import cv2
import os
import numpy as np
from flask import Flask, request, jsonify


# Now confirm that we can reload it, and it still gives the same results
reloaded = tf.keras.models.load_model('path/1693398075', custom_objects={'KerasLayer':hub.KerasLayer})
Labels = ['kristen_stewart', 'maria_sharapova']

app = Flask(__name__)


@app.route('/skin_disease',methods=['GET','POST'])
def skin_disease():

    # ekhane form theke base64 image antechi
    image_data = request.form['image_data']

    # ei function diye asole image er change kortechi color bade to send it to the machine learning moodel
    def upload(filename):
        img = cv2.imread(os.path.join("", filename))
        img = cv2.resize(img, (224, 224))
        img = img / 255
        return img

    # img = upload("maria.jpg")
    # a,r
    # def get_b64_image_data():
    #    with open("maria_b64.txt") as f:
    #        return f.read()

    def pre_result(image):
        x = reloaded.predict(np.asarray([image]))[0]
        classx = np.argmax(x)
        return {Labels[classx]: x[classx]}

    #img_b64 = get_b64_image_data()

    # eida diye muloto base 64 theke asol image a convert kortechi
    def get_cv2_image_from_base_64_string(b64str):
        encoded_data = b64str.split(',')[1]
        nparr = np.frombuffer(base64.b64decode(encoded_data), np.uint8)
        img = cv2.imdecode(nparr,cv2.IMREAD_COLOR)
        return img

    img = get_cv2_image_from_base_64_string(image_data)
    img = cv2.resize(img, (224, 224))
    img = img / 255

    prediction = pre_result(img)

    result = []

    result.append({
        'class': list(prediction.keys())[0],
        'class_probability': (reloaded.predict(np.asarray([img]))*100).tolist()[0]
    })
    print(result)
    response = jsonify(result)

    response.headers.add('Access-Control-Allow-Origin', '*')

    # return list((prediction.keys()))[0]
    return response


if __name__ == '__main__':
    app.run(port=5000)

