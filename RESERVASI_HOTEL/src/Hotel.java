import java.time.LocalDate;

public class Hotel {
    private int noKamar;
    private String pemesan;
    private LocalDate tglMasuk;
    private LocalDate tglKeluar;
    private String tipeKamar;

    public Hotel(String pemesan, LocalDate tglMasuk, LocalDate tglKeluar, String tipeKamar, int noKamar) {
        this.noKamar = noKamar;
        this.pemesan = pemesan;
        this.tglMasuk = tglMasuk;
        this.tglKeluar = tglKeluar;
        this.tipeKamar = tipeKamar;
    }

    // Getter dan Setter
    public int getNoKamar() {
        return noKamar;
    }

    public void setNoKamar(int noKamar) {
        this.noKamar = noKamar;
    }

    public String getPemesan() {
        return pemesan;
    }

    public void setPemesan(String pemesan) {
        this.pemesan = pemesan;
    }

    public LocalDate getTglMasuk() {
        return tglMasuk;
    }

    public void setTglMasuk(LocalDate tglMasuk) {
        this.tglMasuk = tglMasuk;
    }

    public LocalDate getTglKeluar() {
        return tglKeluar;
    }

    public void setTglKeluar(LocalDate tglKeluar) {
        this.tglKeluar = tglKeluar;
    }

    public String getTipeKamar() {
        return tipeKamar;
    }

    public void setTipeKamar(String tipeKamar) {
        this.tipeKamar = tipeKamar;
    }
}
