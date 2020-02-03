package tr.com.cbc.credit.query.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tr.com.cbc.credit.query.Credit;
import tr.com.cbc.credit.query.service.CreditQueryService;
import tr.com.cbc.credit.query.service.ValidationException;

@RestController

public class CreditQueryController {


	@Autowired
	private CreditQueryService krediSorguService;

	
	//private ResponseEntity<List<Kredi>> retVal;
	
	@GetMapping(path = "/query")
	public ResponseEntity<?>  getKredi(@RequestParam(name = "tckn", required = false) String tckn,
			
			@RequestParam(name = "bankaAdi", required = false) String bankaAdi,
			@RequestParam(name = "krediTuru", required = false) String krediTuru,
			@RequestParam(name = "dosyaTarihi", required = false) String dosyaTarihi,
			@RequestParam(name = "krediNumarasi", required = false) String krediNumarasi) throws ValidationException  {
		
	
		try {
			
			List<Credit> krediList = krediSorguService.getKrediListesi(tckn,bankaAdi,krediTuru,dosyaTarihi,krediNumarasi);

			 return ResponseEntity.ok(krediList);

		} catch (ValidationException e) {
			 return ResponseEntity.badRequest().body(e.getMessage());
		}
	
	

	
	
		
	}

	

//	@GetMapping(path = "/credit/query")
//	public Employees getEmployees() {
//		return employeeDao.getAllEmployees();
//	}

//	 @RequestMapping(value = "/sample1",method=RequestMethod.GET)	 
//public ResponseEntity<?> queryCredit(@RequestBody Kredi k)  {
//		 
//		 
//		 try {
//        result.add(k.toString());
//
//      return ResponseEntity.badRequest().body(result);
//		} catch (Exception e) {
//			return ResponseEntity.badRequest().body("salih");
//		}}

}
