package tr.com.cbc.credit.query;

import java.time.LocalDateTime;
import java.util.List;

public class Credit {

	private String dosyaTarihi;
	private String krediNumarasi;
	private String bankaAdi;
	private String krediTuru;
	private String tckn;


	
	public Credit(String dosyaTarihi, String krediNumarasi, String bankaAdi, String krediTuru, String tckn) {
		super();
		this.dosyaTarihi = dosyaTarihi;
		this.krediNumarasi = krediNumarasi;
		this.bankaAdi = bankaAdi;
		this.krediTuru = krediTuru;
		this.tckn = tckn;
	}

	public String getDosyaTarihi() {
		return dosyaTarihi;
	}

	public void setDosyaTarihi(String dosyaTarihi) {
		this.dosyaTarihi = dosyaTarihi;
	}

	public String getKrediNumarasi() {
		return krediNumarasi;
	}

	public void setKrediNumarasi(String krediNumarasi) {
		this.krediNumarasi = krediNumarasi;
	}

	public String getBankaAdi() {
		return bankaAdi;
	}

	public void setBankaAdi(String bankaAdi) {
		this.bankaAdi = bankaAdi;
	}

	public String getKrediTuru() {
		return krediTuru;
	}

	public void setKrediTuru(String krediTuru) {
		this.krediTuru = krediTuru;
	}

	public String getTckn() {
		return tckn;
	}

	public void setTckn(String tckn) {
		this.tckn = tckn;
	}

	@Override
	public String toString() {
		return "QueryObject[" + "tckn=" + tckn + ", krediNumarasi=" + krediNumarasi + ", bankaAdi='" + bankaAdi + '\''
				+ ", krediTuru='" + krediTuru + '\'' + ", dosyaTarihi='" + dosyaTarihi + '\'' + ']';
	}
	

	

}
