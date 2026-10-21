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
    kernel = [0.1, 0.15, 0.5, 0.15, 0.1]
    kernel_fft = np.fft.rfft(kernel)
    kernel_freqs = np.fft.rfftfreq(len(kernel))

    duration = 2
    sample_rate = 200
    time = np.arange(int(duration * sample_rate)) / sample_rate

    signal = sine_wave(time, 1, 5) + cosine_wave(time, 2, 3)

    sine_series = sine_fourier_series(time)
    cosine_series = cosine_fourier_series(time)

    signal_fft = np.fft.rfft(signal)
    fft_approx = np.fft.irfft(signal_fft, n=len(signal))

    sin_coefficients, _, _, _ = la.lstsq(sine_series, signal, rcond=None)
    cos_coefficients, _, _, _ = la.lstsq(cosine_series, signal, rcond=None)

    sin_wave_approximation = sine_series @ sin_coefficients
    cos_wave_approximation = cosine_series @ cos_coefficients

    plt.figure(figsize=(8, 4), dpi=150)
    plt.scatter(time, signal, color="k", s=10, label="Signal")
    plt.plot(
        time,
        sin_wave_approximation,
        c="b",
        lw=0.8,
        label="Sines",
    )
    plt.plot(
        time,
        cos_wave_approximation,
        c="r",
        lw=0.8,
        label="Cosines",
    )
    plt.plot(
        time,
        fft_approx,
        c="g",
        lw=0.8,
        label="FFT",
    )
    plt.grid()
    plt.legend()
    plt.tight_layout()
    plt.show()

    signal_freqs = np.fft.rfftfreq(len(signal), d=1 / sample_rate)
    kernel = np.ones_like(signal_freqs)
    kernel[np.abs(signal_freqs) < 4] = 0.0

    y = signal_fft * kernel
    y = np.fft.irfft(y, n=len(signal))

    plt.figure(figsize=(8, 4), dpi=150)
    plt.plot(time, signal, label="Original")
    plt.plot(time, y, label="Filtered")
    plt.grid()
    plt.legend()
    plt.tight_layout()
    plt.show()
