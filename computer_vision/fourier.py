import matplotlib.pyplot as plt
import numpy as np


def sine_wave(
    time: np.ndarray, amplitude: float, frequency: float, phase: float = 0.0
) -> np.ndarray:
    return amplitude * np.sin(2 * np.pi * frequency * time - phase)


def cosine_wave(
    time: np.ndarray, amplitude: float, frequency: float, phase: float = 0.0
) -> np.ndarray:
    return amplitude * np.cos(2 * np.pi * frequency * time - phase)


def complex_exponential_wave(
    time: np.ndarray, amplitude: float, frequency: float, phase: float = 0.0
) -> np.ndarray:
    return amplitude * np.exp(2j * np.pi * frequency * time - phase)


if __name__ == "__main__":
    duration = 2
    sample_rate = 100
    time = np.arange(int(duration * sample_rate)) / sample_rate
    print(f"samples: {len(time)}")

    signal = (
        sine_wave(time, 1, 3) + sine_wave(time, 1, 2)
        # + np.random.normal(0, 0.3, size=time.size)
    )

    fft = np.fft.rfft(signal)
    freqs = np.fft.rfftfreq(len(signal), 1 / sample_rate)
    magnitude = np.abs(fft)

    print(f"fft real: {np.sort(np.real(fft))}")
    print(f"fft imag: {np.sort(np.imag(fft))}")

    approx = np.zeros_like(signal)
    for i, x in enumerate(fft, start=0):
        approx += complex_exponential_wave(time, x, i)

    plt.figure(figsize=(8, 4), dpi=150)
    plt.scatter(time, signal, s=10, label="Signal")
    plt.plot(time, approx, lw=0.75, label="Fourier Approximation")
    plt.grid()
    plt.legend()
    plt.tight_layout()
    plt.show()

    plt.figure(figsize=(8, 4), dpi=150)
    plt.plot(freqs, magnitude, lw=0.75, label="frequency")
    for f in [2, 3]:
        plt.axvline(f, color="r", lw=0.75, ls="--")
    plt.grid()
    plt.legend()
    plt.tight_layout()
    plt.show()
