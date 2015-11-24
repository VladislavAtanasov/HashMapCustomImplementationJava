package HashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ComparisonMapsClass {

	public static String random() {

		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

	public static void main(String[] args) {
		Map<String, Integer> original = new HashMap<>();
		Map<String, Integer> interfaceImplemented = new CustomMap<>();
		CustomHashMap<String, Integer> custom = new CustomHashMap<>();

		/**
		 * I made some tests on putting in the map. Statistics: Most of the time
		 * - 1.InterfaceImplemented 2.CustomHashMap 3.Original
		 */

		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			original.put(random(), i);
		}
		long finished = System.currentTimeMillis();

		System.out.println("Original time: " + ((finished - start) / 1000.0));

		start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			interfaceImplemented.put(random(), i);
		}
		finished = System.currentTimeMillis();

		System.out.println("InterfaceImplemented time: " + ((finished - start) / 1000.0));

		start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			custom.put(random(), i);
		}
		finished = System.currentTimeMillis();

		System.out.println("CustomHashMap time: " + ((finished - start) / 1000.0));

	}

}
