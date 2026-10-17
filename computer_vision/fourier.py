import matplotlib.pyplot as plt
import numpy as np


def sine_wave(interval: np.ndarray, frequency: float) -> np.ndarray:
    return np.sin(2 * np.pi * frequency * interval / len(interval))


if __name__ == "__main__":
    N = 100
    interval = np.linspace(0, np.pi, N)
    wave1 = sine_wave(interval, 50)
    wave2 = sine_wave(interval, 100)

    plt.figure(figsize=(8, 5), dpi=150)
    plt.title("Sine Waves")

    plt.plot(interval, wave1, "o-", label="wave1")
    plt.plot(interval, wave2, "o-", label="wave2")

    plt.legend()
    plt.grid()
    plt.tight_layout()
    plt.show()
