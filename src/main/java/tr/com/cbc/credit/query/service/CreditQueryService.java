package tr.com.cbc.credit.query.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Service;

import tr.com.cbc.credit.query.Credit;

@Service
public class CreditQueryService {

	public java.util.List<Credit> getKrediListesi(String tckn, String bankaAdi, String krediTuru, String dosyaTarihi,
			String krediNumarası) throws ValidationException {

		if (tckn != null && !tckn.isEmpty() && (tcknKontrol(tckn) == false)) {

			if (isAllNumeric(tckn) == false) {
				throw new ValidationException("hata var : " + " TCKN nümerik olmalıdır.");
			} else {
				throw new ValidationException("hata var : " + " Geçersiz TCKN.");
			}

		}


		if (krediTuru != null && !krediTuru.isEmpty()) {

			if (isAllNumeric(krediTuru) == true) {
				throw new ValidationException("hata var : " + " Kredi Türü string  olmalıdır.");
			}

			else if (krediTuru.equals("Konut Kredisi") == false && krediTuru.equals("Taşıt Kredisi") == false
					&& krediTuru.equals("İhtiyaç Kredisi") == false) {
				throw new ValidationException("hata var : " + " Geçersiz kredi türü");
			}
		}

		if (krediNumarası != null && !krediNumarası.isEmpty()) {

			if (isAllNumeric(krediNumarası) == false) {
				throw new ValidationException("hata var : " + " Kredi numarası nümerik olmalıdır.");
			}

		}

		if (dosyaTarihi != null && !dosyaTarihi.isEmpty()) {

			if (!isAllNumeric(dosyaTarihi)) {
				throw new ValidationException("hata var : " + " Dosya tarihi nümerik olmalıdır.");
			} else if (!validateJavaDate(dosyaTarihi)) {

				throw new ValidationException("hata var : " + " DosyaTarihi formata uygun değil.");
			}

		}


		if (bankaAdi != null && !bankaAdi.isEmpty()) {

			if (isAllNumeric(bankaAdi) == true) {
				throw new ValidationException("hata var : " + " bankaAdi String olmalıdır.");
			}

			else if (bankaAdi.equals("Akbank") == false && bankaAdi.equals("Halkbank") == false
					&& bankaAdi.equals("Ziraat") == false && bankaAdi.equals("Garanti") == false
					&& bankaAdi.equals("Finansbank") == false) {
				throw new ValidationException("hata var : " + " Geçersiz banka adı");
			}

		}

		java.util.List<Credit> forReturn = new ArrayList<Credit>();
		String filePath = "C:\\Users\\svsal\\Desktop\\KrediDosyaları";
		boolean isDirectory = true;
		if (dosyaTarihi != null && !dosyaTarihi.isEmpty()) {
			filePath = filePath + "\\" + dosyaTarihi + "_krediler.txt";
			isDirectory = false;
		}
        
		File directory = new File(filePath);
         if(!directory.exists())
         {
        	 throw new ValidationException("hata var : " + " Dosya bulunmadı");
         }
		File[] fileArray = isDirectory ? directory.listFiles() : new File[] { directory };

		for (int j = 0; j < fileArray.length; j++) {

			BufferedReader reader = null;
			try {
				try {
					reader = new BufferedReader(new FileReader(fileArray[j]));
				} catch (FileNotFoundException e) {

					e.printStackTrace();
					throw new ValidationException("hata var : Dosya bulunamadı.");
				}
//				reader = new BufferedReader(new FileReader(fileArray[j]));

				String fileDateInner = fileArray[j].getName().substring(0, 8);
				String satir = null;
				while ((satir = reader.readLine()) != null) {
					String[] arrOfStr = satir.split(";", 5);

					boolean krediNoOk = true;
					boolean tcknOk = true;
					boolean bankaAdiOk = true;
					boolean krediTuruOk = true;
					boolean fileDateInnerOK = true;

					if (krediNumarası != null && !krediNumarası.isEmpty()) {

						krediNoOk = arrOfStr[0].equals(krediNumarası);

					}

					if (tckn != null && !tckn.isEmpty()) {

						tcknOk = arrOfStr[1].equals(tckn);
					}

					if (bankaAdi != null && !bankaAdi.isEmpty()) {

						bankaAdiOk = arrOfStr[2].equals(bankaAdi);

					}

					if (krediTuru != null && !krediTuru.isEmpty()) {

						krediTuruOk = arrOfStr[4].equals(krediTuru);

					}

					if (dosyaTarihi != null && !dosyaTarihi.isEmpty()) {

						fileDateInnerOK = fileDateInner.equals(dosyaTarihi);

					}

					if (krediNoOk && tcknOk && bankaAdiOk && krediTuruOk && fileDateInnerOK) {
						forReturn.add(new Credit(fileDateInner, arrOfStr[0], arrOfStr[2], arrOfStr[4], arrOfStr[1]));
					}

				}
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
			
				e.printStackTrace();
			} finally {
				try {
					
					if(reader!=null)
					{
						reader.close();
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		return forReturn;
	}

	private boolean tcknKontrol(String tckn) {

		String id = tckn;

		if (id.length() != 11)
			return false;

		char[] chars = id.toCharArray();
		int[] a = new int[11];

		for (int i = 0; i < 11; i++) {
			a[i] = chars[i] - '0';
		}

		if (a[0] == 0)
			return false;
		if (a[10] % 2 == 1)
			return false;

		if (((a[0] + a[2] + a[4] + a[6] + a[8]) * 7 - (a[1] + a[3] + a[5] + a[7])) % 10 != a[9])
			return false;

		if ((a[0] + a[1] + a[2] + a[3] + a[4] + a[5] + a[6] + a[7] + a[8] + a[9]) % 10 != a[10])
			return false;

		return true;
	}

	public boolean isAllNumeric(String code) {
		for (int i = 0; i < code.length(); i++) {
			if (!Character.isDigit(code.charAt(i)))
				return false;
		}
		return true;
	}

	public static boolean validateJavaDate(String date) throws ValidationException {

		String DATE_FORMAT = "yyyyMMdd";

		try {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			df.setLenient(false);
			df.parse(date);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;

	}

	public boolean isValidDate(String date) {
		int date1 = Integer.valueOf(date);

		int DiziAdı[] = new int[1000];
		int i = 0;
		while (date1 > 0) {

			DiziAdı[i] = date1 % 10;

			date1 = date1 / 10;

			System.out.println(i + " " + DiziAdı[i]);
			i++;

		}

		if (i != 8)
			return false;
		if (i == 8 && DiziAdı[1] > 1)
			return false;

		if (i == 8 && DiziAdı[3] > 3) {
			if (DiziAdı[2] > 1) {
				return false;
			}

		}

		if (i == 8 && DiziAdı[1] == 1) {
			if (DiziAdı[0] > 2) {
				return false;
			}

		}

		return true;

	}
}
