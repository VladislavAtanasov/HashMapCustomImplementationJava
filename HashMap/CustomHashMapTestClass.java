package HashMap;

import java.util.UUID;

import org.junit.Test;

import junit.framework.TestCase;

public class CustomHashMapTestClass extends TestCase {

	private CustomHashMap<String, Integer> map;
	private CustomHashMap<String, Integer> other;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.map = new CustomHashMap<>();
		this.other = new CustomHashMap<>();
	}

	@Test
	public void testPut() {
		for (int i = 0; i < 10000; i++) {
			map.put(random(), i);
		}
		assertEquals(map.size(), 10000);
	}

	@Test
	public void testIsEmpty() {
		this.testPut();
		assertFalse(map.isEmpty());
	}

	@Test
	public void testKeySet() {
		this.testPut();
		assertEquals(10000, map.keySet().size());
	}

	@Test
	public void testEntrySet() {
		this.testPut();
		assertEquals(10000, map.entrySet().size());
	}

	@Test
	public void testGet() {
		map.put("Hash", 21);
		assertEquals((Integer) 21, map.get("Hash"));
		assertNull(map.get("hash"));
	}

	@Test
	public void testRemove() {
		map.put("Hash", 21);
		map.put("Levski", 52);
		assertEquals((Integer) 21, map.remove("Hash"));
		assertNull(map.remove("Hashing"));
	}

	@Test
	public void testRemoveTwoParams() {
		map.put("Hash", 21);
		map.put("Levski", 52);
		assertTrue(map.remove("Hash", 21));
		assertFalse(map.remove("Hash", 31));
		assertFalse(map.remove("Test", 21));
	}

	@Test
	public void testGetOrDefault() {
		map.put("Hash", 21);
		map.put("Levski", 52);
		assertEquals((Integer) 21, map.getOrDefault("Hash", 1));
		assertEquals((Integer) 1, map.getOrDefault("Hashq", 1));
	}

	@Test
	public void testPutIfAbsent() {
		map.put("Hash", 21);
		map.put("Levski", 52);
		assertTrue(map.putIfAbsent("HashMap", 11));
		assertFalse(map.putIfAbsent("Levski", 32));
	}

	@Test
	public void testPutAll() {
		map.put("Hash", 21);
		map.put("Levski", 52);
		other.put("Game of thrones", 7);
		map.putAll(other);
		assertEquals(3, map.entrySet().size());
		assertEquals(3, map.keySet().size());
		assertEquals(3, map.values().size());
	}

	@Test
	public void testReplace() {
		map.put("Hack", 5);
		map.put("Iniesta", 25);
		assertEquals((Integer) 25, map.replace("Iniesta", 5));
		assertNull(map.replace("LOl", 45));
	}

	@Test
	public void testReplaceThreeParams() {
		map.put("Hack", 5);
		map.put("Iniesta", 25);
		assertTrue(map.replace("Iniesta", 25, 16));
		assertFalse(map.replace("Hack", 85, 17));
	}

	@Test
	public void testContainsKey() {
		map.put("Hack", 5);
		map.put("Iniesta", 25);
		assertTrue(map.containsKey("Iniesta"));
		assertFalse(map.containsKey("Rack"));
	}

	@Test
	public void testClear() {
		map.put("Hack", 5);
		map.put("Iniesta", 25);
		map.clear();
		assertEquals(0, map.entrySet().size());
		assertEquals(0, map.keySet().size());
	}

	@Test
	public void testSize() {
		assertEquals(0, map.size());
		map.put("TV", 23);
		assertEquals(1, map.size());
	}

	@Test
	public void testContainsValue() {
		map.put("Hack", 5);
		assertTrue(map.containsValue(5));
		assertFalse(map.containsValue(20));
	}

	private String random() {

		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

}
