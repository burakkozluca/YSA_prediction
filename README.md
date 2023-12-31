# Neural Network Project

This project implements a simple neural network for a classification task using the Neuroph library. The neural network is trained on a dataset, and its performance is evaluated on a separate test dataset.

## Overview

- `Program.java`: The main class that sets up and runs the neural network training and testing process.
- `YSA.java`: The neural network class that contains methods for training, testing, and making predictions.
- `HataGrafik.java`: A class for displaying the training error chart.

## Getting Started

1. Clone the repository to your local machine.
2. Open the project in your preferred Java IDE.
3. Ensure that the Neuroph library is added to the project.
4. Run the `Program` class.

## Configuration

In the `Program` class:

- Adjust the parameters in the `YSA` constructor for your specific neural network configuration.
- Update the file paths for training and test datasets if needed.

## Usage

1. Run the `Program` class to train and test the neural network.
2. View the training error chart generated by `HataGrafik`.

## File Structure

- `Begitim.txt`: Training dataset file.
- `Btest.txt`: Test dataset file.

## Notes

- Ensure that the dataset files (`Begitim.txt` and `Btest.txt`) are properly formatted.
- The neural network model is saved to a file named `ysa.nnet` after training.
