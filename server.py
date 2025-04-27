from flask import Flask, request, jsonify
import cv2
from pyzbar.pyzbar import decode
import numpy as np

app = Flask(__name__)

@app.route("/decode", methods=["POST"])
def decode_barcode():
    if "image" not in request.files:
        return jsonify({"error": "No image part"}), 400

    file = request.files["image"]
    npimg = np.frombuffer(file.read(), np.uint8)
    img = cv2.imdecode(npimg, cv2.IMREAD_COLOR)

    barcodes = decode(img)
    results = [barcode.data.decode('utf-8') for barcode in barcodes]

    return jsonify({"barcodes": results})

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
