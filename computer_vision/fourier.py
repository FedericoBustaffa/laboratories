import matplotlib.pyplot as plt
import numpy as np


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


duration = 2
sample_rate = 50
time = np.arange(int(duration * sample_rate)) / sample_rate

signal = 2 * np.sin(2 * np.pi * 3 * time) + 4 * np.sin(2 * np.pi * 2 * time)

contributions, partial_sums = fourier_series(time, signal, n_harmonics=30)

plt.figure(figsize=(9, 5))
plt.scatter(time, signal, label="Segnale originale", s=12)

for m in [1, 10, 20]:
    plt.plot(time, partial_sums[m - 1], label=f"{m} contributi")

plt.grid()
plt.legend()
plt.tight_layout()
plt.show()
