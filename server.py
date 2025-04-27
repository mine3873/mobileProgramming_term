from flask import Flask, request, jsonify
import pyzxing
import os

app = Flask(__name__)

scanner = pyzxing.BarCodeReader()

@app.route("/decode", methods=["POST"])
def decode_barcode():
    if "image" not in request.files:
        return jsonify({"error": "No image part"}), 400

    file = request.files["image"]
    
    filepath = "temp_image.png"
    file.save(filepath)

    results = scanner.decode(filepath)

    barcodes = []
    if results:
        for r in results:
            if 'parsed' in r:
                barcodes.append(r['parsed'])

    os.remove(filepath)

    return jsonify({"barcodes": barcodes})

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
