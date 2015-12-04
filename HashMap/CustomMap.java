package HashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * The map does not allow null keys or values. It is realized by a inner array
 * of Lists that contain the Entries. Anytime a pair would be put, a function
 * calculates the index on which the pair would be positioned. The map can be
 * instantiated with two constructors. The one sets a positive Integer value,
 * the other is unparameterized and sets the size of the array to the defined
 * constant value.
 * 
 * @author Homes
 *
 * @param <K>
 * @param <V>
 */

public class CustomMap<K, V> implements Map<K, V> {

	final class CustomEntry implements Entry<K, V> {
		private final K key;
		private V value;

		public CustomEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V old = this.value;
			this.value = value;
			return old;
		}

		@Override
		public String toString() {
			return "[key=" + key + ", value=" + value + "]";
		}

	}

	private static final int SIZE = 100;
	private List<Entry<K, V>>[] entry;
	private Set<K> keys;
	private Set<Entry<K, V>> pairs;
	private Collection<V> values;

	@SuppressWarnings("unchecked")
	public CustomMap() {
		entry = new ArrayList[SIZE];
		keys = new HashSet<>();
		pairs = new HashSet<>();
		values = new ArrayList<>();
	}

	@SuppressWarnings("unchecked")
	public CustomMap(int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Size cannot be a negative number.");
		}

		entry = new ArrayList[size];
		keys = new HashSet<>();
		pairs = new HashSet<>();
		values = new ArrayList<>();
	}

	/**
	 * Adds a new pair associated with the given key and the given value.
	 * 
	 * @param key
	 * @param value
	 */

	@Override
	public V put(K key, V value) {
		if (value == null || key == null) {
			throw new IllegalArgumentException("Map cannot have a null value or null key.");
		}
		int keyIndex = getIndexFor(key);
		if (entry[keyIndex] == null) {
			entry[keyIndex] = new ArrayList<>();
		}
		Entry<K, V> pair = new CustomEntry(key, value);
		Entry<K, V> checkPair = getPairFromKey(entry[keyIndex], key);
		if (checkPair != null) {
			entry[keyIndex].remove(checkPair);
			values.remove(checkPair.getValue());
			pairs.remove(checkPair);
		}
		entry[keyIndex].add(pair);
		pairs.add((Entry<K, V>) pair);
		keys.add(key);
		values.add(value);
		return value;
	}

	/**
	 * Gets the value that is associated with the given key.
	 * 
	 * @param key
	 * @return
	 */

	@Override
	public V get(Object key) {
		if (key == null) {
			throw new IllegalArgumentException("No null keys.");
		}
		int keyIndex = getIndexFor(key);
		if (entry[keyIndex] != null) {
			for (Entry<K, V> pair : entry[keyIndex]) {
				if (pair.getKey().equals(key)) {
					return pair.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * Removes the pair that is associated with the given key and return its
	 * value or null if there is no such key.
	 * 
	 * @param key
	 * @return Type V
	 */

	@Override
	public V remove(Object key) {
		if (key == null) {
			throw new IllegalArgumentException("HashMap cannot have null key.");
		}

		if (this.containsKey(key)) {
			for (Entry<K, V> p : entry[getIndexFor(key)]) {
				if (p.getKey().equals(key)) {
					entry[getIndexFor(key)].remove(p);
					pairs.remove(p);
					keys.remove(key);
					values.remove(p.getValue());
					return p.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * Removes the pair, specified by the given key and value.
	 * 
	 * @param key
	 * @param value
	 * @return boolean
	 */

	@Override
	public boolean remove(Object key, Object value) {
		if (key == null || value == null) {
			throw new IllegalArgumentException("HashMap cannot have null key or values.");
		}

		if (this.containsKey(key) && this.get(key).equals(value)) {
			this.remove(key);
			return true;
		}
		return false;
	}

	/**
	 * If the map contains the given key, this method returns its own value.
	 * Otherwise, it returns the defaultValue.
	 * 
	 * @param key
	 * @param defaultValue
	 * @return Type V
	 */

	public V getOrDefault(Object key, V defaultValue) {
		if (defaultValue == null) {
			throw new IllegalArgumentException("HashMap cannot have null values.");
		}

		if (this.containsKey(key)) {
			return this.get(key);
		}
		return defaultValue;
	}

	/**
	 * Put a new key-value mapping, if the map does not contain already the
	 * given key.
	 * 
	 * @param key
	 * @param value
	 * @return boolean
	 */

	public V putIfAbsent(K key, V value) {
		if (!this.containsKey(key)) {
			this.put(key, value);
			return value;
		}

		return null;
	}

	/**
	 * Puts all of the key-value mappings from the given map to the map that
	 * this method is executed to.
	 * 
	 * @param other
	 */

	@Override
	public void putAll(Map<? extends K, ? extends V> other) {
		if (other.containsKey(null) || other.containsValue(null)) {
			throw new IllegalArgumentException("Given map has null key/s or value/s.");
		}

		for (Entry<? extends K, ? extends V> pair : other.entrySet()) {
			this.put(pair.getKey(), pair.getValue());
		}
	}

	/**
	 * If the map contains the given key, replace its own oldValue that is
	 * mapped to, with the given value. Returns the oldValue or null.
	 * 
	 * @param key
	 * @param value
	 * @return Type V
	 */

	public V replace(K key, V value) {
		if (key == null) {
			throw new IllegalArgumentException("Not null keys.");
		}

		if (this.containsKey(key)) {
			V oldValue = this.get(key);
			this.put(key, value);
			return oldValue;
		}
		return null;
	}

	/**
	 * If there is a Pair(key, oldValue) in the map, this method replace the
	 * oldValue with the newValue in the pair. Otherwise, does nothing and
	 * returns false.
	 * 
	 * @param key
	 * @param oldValue
	 * @param newValue
	 * @return boolean
	 */

	public boolean replace(K key, V oldValue, V newValue) {
		if (key == null || oldValue == null || newValue == null) {
			throw new IllegalArgumentException("Not null keys or values.");
		}

		if (this.containsKey(key) && this.get(key).equals(oldValue)) {
			this.put(key, newValue);
			return true;
		}

		return false;
	}

	/**
	 * Checks whether a given key has a mapping in the Map.
	 * 
	 * @param key
	 * @return boolean
	 */

	@Override
	public boolean containsKey(Object key) {
		return keys.contains(key);
	}

	/**
	 * Checks whether a given value has a mapping in the Map.
	 * 
	 * @param value
	 * @return boolean
	 */
	@Override
	public boolean containsValue(Object value) {
		return values.contains(value);
	}

	/**
	 * Returns all of the existing pairs in the Map.
	 * 
	 * @return
	 * 
	 * @return Set<Pair<K, V>>
	 */

	@Override
	public Set<Entry<K, V>> entrySet() {
		return pairs;
	}

	/**
	 * Returns all of keys that have a mapping.
	 * 
	 * @return Set<K>
	 */

	public Set<K> keySet() {
		return keys;
	}

	/*
	 * Returns Collection<V> of all of the values(An ArrayList<V>).
	 */

	@Override
	public Collection<V> values() {
		return values;
	}

	/*
	 * Removes every pair in the map.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		entry = new ArrayList[SIZE];
		pairs.clear();
		keys.clear();
		values.clear();
	}

	/**
	 * Returns the number of pairs in the Map.
	 * 
	 * @return Integer
	 */
	public int size() {
		return pairs.size();
	}

	/**
	 * Checks if the map contains any pair.
	 * 
	 * @return boolean
	 */

	@Override
	public boolean isEmpty() {
		return this.size() == 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;

		if (!(o instanceof Map))
			return false;
		Map<K, V> m = (Map<K, V>) o;
		if (m.entrySet().size() != this.entrySet().size()) {
			return false;
		}
		return m.entrySet().equals(this.entrySet());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(entry);
		result = prime * result + ((keys == null) ? 0 : keys.hashCode());
		result = prime * result + ((pairs == null) ? 0 : pairs.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		return result;
	}

	/**
	 * String representation of the map.
	 */
	@Override
	public String toString() {
		return "{" + pairs + "}";
	}

	/**
	 * Get the pair from the HashMap that has the given key. list is the value
	 * for the current index of the array(e.x. entry[getIndexFor(key)].
	 * 
	 * @param entry
	 * @param key
	 * @return
	 */
	private Entry<K, V> getPairFromKey(List<Entry<K, V>> entry, K key) {
		for (Entry<K, V> pair : entry) {
			if (pair.getKey().equals(key)) {
				return pair;
			}
		}

		return null;
	}

	/**
	 * Calculates the index of which key will be in the inner array of the
	 * HashMap
	 * 
	 * @param key
	 * @return Integer
	 */
	private int getIndexFor(Object key) {
		return Math.abs(key.hashCode() % SIZE);
	}

	public static void main(String[] args) {
		Map<String, Double> hm = new CustomMap<>();
		hm.put("Vladislav", 6.0);
		hm.put("Vladislav", 7.0);
		hm.put("Pesho", 7.1);
		System.out.println(hm.entrySet());
		Map<String, Double> hm1 = new HashMap<>();
		hm1.put("Vladislav", 6.0);
		hm1.put("Vladislav", 7.0);
		hm1.put("Pesho", 7.1);
		// hm1.put("klkl", 34.1);
		System.out.println(hm1.entrySet());
		System.out.println(hm.equals(hm1));
	}

}
