package es.instavino.lousy;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Prueba {

	public static void main(String... args) {
		String dateString = "1435486536,1435486547,1435486669,1435486705,1435486717,1435486788,1435486797,1435486806,1435486849,1435486930,1435486978,1435486983,1435487082,1435487098,1435487111,1435487122,1435487180,1435487209,1435487308,1435487354,1435487399,1435487402,1435487402,1435487744,1435487786,1435487788,1435487817,1435487847,1435487859,1435487879,1435488009,1435488067,1435488095,1435488149,1435488158,1435488170,1435488192,1435488192,1435488249,1435488273,1435488275,1435488338,1435488380,1435488414,1435488452,1435488485,1435488539,1435488549,1435488562,1435488579,1435488637,1435488672,1435488691,1435488748,1435488761,1435488769,1435488775,1435488797,1435488868,1435488895,1435488931";
		String dates[] = dateString.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		for(String d:dates){
			Date date = new Date(Long.parseLong(d)*1000);
			System.out.println(sdf.format(date));
		}
 	}

}
