import java.util.Map;

public class MethodEntry extends ScopeEntry {

	public MethodEntry(String name, Type returnType) {
		super(name, returnType);
	}

	/**
	 * Only instances of VariableEntry can be inserted into the scope of a
	 * MethodEntry, i.e., only the formal parameters can be inserted.
	 */
	public boolean addBinding(String name, Entry symTabEntry) {
		if (symTabEntry.getClass() != VariableEntry.class) return false;
		if (lookup(name) == null) {
			localSymtab.put(name, symTabEntry);
			return true;
		}
		return false;
	}

	/**
	 * Returns a String representation of the method signature. E.g., int m(int
	 * i, char c);
	 */
	public String toString() {
		// The formal parameters are recorded in the super class,
		// i.e. 'ScopeEntry'.
		// Use the iterator methods (reset(), next(), hasMore())
		// Use them to retrieve the parameters in the proper order.
		// Note that the superclass method toString() cannot be used
		// here because the delimiters are different (comma here
		// versus semicolon in the superclass method).
		String str = this.type() + " " + this.name() + "(";
		
		boolean first = true;
		for (Map.Entry<String, Entry> entry : localSymtab.entrySet()) {
			String val = entry.getValue().toString();
			if (first) {
				str += val;
				first = false;
			} else {
				str += ", " + val;
			}
		}
		str += ")";
		return str;
	}
} // End of class MethodEntry
