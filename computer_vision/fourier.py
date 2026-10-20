import matplotlib.pyplot as plt
import numpy as np
import numpy.linalg as la


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


def sine_fourier_series(time: np.ndarray) -> np.ndarray:
    """generates each term of the fourier series only using sine waves"""
    n_harmonics = len(time)
    waves = np.zeros((n_harmonics, n_harmonics))
    waves[0] = np.ones(n_harmonics)
    for i in range(1, n_harmonics):
        waves[i] = np.sin(2 * np.pi * i * time)

    return waves


def cosine_fourier_series(time: np.ndarray) -> np.ndarray:
    """generates each term of the fourier series only using cosine waves"""
    n_harmonics = len(time)
    waves = np.zeros((n_harmonics, n_harmonics))
    waves[0] = np.ones(n_harmonics)
    for i in range(1, n_harmonics):
        waves[i] = np.cos(2 * np.pi * i * time)

    return waves


if __name__ == "__main__":
    duration = 2
    sample_rate = 100
    time = np.arange(int(duration * sample_rate)) / sample_rate
    print(f"samples: {len(time)}")

    signal = (
        sine_wave(time, 1, 3) + cosine_wave(time, 1, 2)
        # + np.random.normal(0, 0.3, size=time.size)
    )

    sine_series = sine_fourier_series(time)
    cosine_series = cosine_fourier_series(time)
    series = sine_series + cosine_series

    sin_coefficients, _, _, _ = la.lstsq(sine_series, signal, rcond=None)
    cos_coefficients, _, _, _ = la.lstsq(cosine_series, signal, rcond=None)
    coefficients, _, _, _ = la.lstsq(series, signal, rcond=None)

    sin_wave_approximation = sine_series @ sin_coefficients
    cos_wave_approximation = cosine_series @ cos_coefficients
    wave_approximation = series @ coefficients

    # fft = np.fft.rfft(signal)
    # freqs = np.fft.rfftfreq(len(signal), 1 / sample_rate)
    # magnitude = np.abs(fft)

    plt.figure(figsize=(8, 4), dpi=150)
    plt.scatter(time, signal, color="k", s=10, label="Signal")
    plt.plot(
        time,
        sin_wave_approximation,
        c="b",
        lw=0.75,
        label="Sines Approximation",
    )
    plt.plot(
        time,
        cos_wave_approximation,
        c="r",
        lw=0.75,
        label="Cosines Approximation",
    )
    plt.plot(
        time,
        wave_approximation,
        c="g",
        lw=0.75,
        label="Sines + Cosines Approximation",
    )
    plt.grid()
    plt.legend()
    plt.tight_layout()
    plt.show()

    # plt.figure(figsize=(8, 4), dpi=150)
    # plt.plot(freqs, magnitude, lw=0.75, label="frequency")
    # for f in [2, 3]:
    #     plt.axvline(f, color="r", lw=0.75, ls="--")
    # plt.grid()
    # plt.legend()
    # plt.tight_layout()
    # plt.show()
