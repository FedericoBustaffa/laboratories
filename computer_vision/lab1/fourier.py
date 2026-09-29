import matplotlib.pyplot as plt
import numpy as np


def generate_wave(
    timepoints: np.ndarray,
    amplitude: float = 1.0,
    frequency: float = 1.0,
) -> np.ndarray:
    return amplitude * np.sin(2 * np.pi * frequency * timepoints)


if __name__ == "__main__":
    N = 1000
    sampling_rate = 1 / N
    timepoints = np.arange(N) * sampling_rate
    wave1 = generate_wave(timepoints, amplitude=1, frequency=20.0)
    wave2 = generate_wave(timepoints, amplitude=1, frequency=50.0)
    wave3 = wave1 + wave2

    wave3_spectrum = np.fft.rfft(wave3)
    wave3_frequencies = np.fft.rfftfreq(N, d=sampling_rate)

    plt.figure(figsize=(8, 5), dpi=150)
    plt.title("Waves")
    plt.plot(timepoints, wave1, label="wave1: A=1, f=440")
    plt.plot(timepoints, wave2, label="wave2: A=1, f=230")
    plt.plot(timepoints, wave3, label="wave3")
    plt.xlabel("Time")
    plt.ylabel("Amplitude")
    plt.legend()
    plt.grid()
    plt.tight_layout()
    plt.show()

    plt.figure(figsize=(8, 5), dpi=150)
    plt.title("Fourier")
    plt.plot(wave3_frequencies, np.abs(wave3_spectrum))
    plt.xlabel("Frequency")
    plt.ylabel("Magnitude")
    plt.grid()
    plt.tight_layout()
    plt.show()
