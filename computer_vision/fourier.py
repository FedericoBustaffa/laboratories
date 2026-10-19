import matplotlib.pyplot as plt
import numpy as np


def sinusoid(time: np.ndarray, amplitude: float, frequency: float) -> np.ndarray:
    duration = np.abs(time.max() - time.min())
    return amplitude * np.sin(2 * np.pi * frequency * time / duration)


def fourier_series(time, signal, n_harmonics):
    dt = time[1] - time[0]
    period = len(time) * dt

    contributions = []

    for k in range(1, n_harmonics + 1):
        sine = np.sin(2 * np.pi * k * time / period)
        cosine = np.cos(2 * np.pi * k * time / period)

        # Coefficienti tramite proiezione
        a = 2 / len(time) * np.sum(signal * cosine)
        b = 2 / len(time) * np.sum(signal * sine)

        contributions.append(a * cosine)
        contributions.append(b * sine)

    contributions = np.array(contributions)
    partial_sums = np.cumsum(contributions, axis=0)

    return contributions, partial_sums


duration = 100
sample_rate = 1
time = np.arange(int(duration * sample_rate)) / sample_rate
print(len(time))

signal = sinusoid(time, 1, 3) + sinusoid(time, 3, 1)

contributions, partial_sums = fourier_series(time, signal, n_harmonics=30)

plt.figure(figsize=(9, 5))
plt.scatter(time, signal, label="Segnale originale", s=12)

for m in [10, 20]:
    plt.plot(time, partial_sums[m - 1], label=f"{m} contributi")

plt.grid()
plt.legend()
plt.tight_layout()
plt.show()
