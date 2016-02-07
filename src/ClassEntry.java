import java.util.Map;

public class ClassEntry extends ScopeEntry {
	
	ClassEntry classEntry;
	
	public ClassEntry(String name) {
		super(name);
	}

	/**
	 * Only instances of VariableEntry, MethodEntry, or ClassEntry can be
	 * inserted into a class scope. If symTabEntry is a VariableEntry,
	 * MethodEntry, or ClassEntry, then insert it into the local symbol table
	 * declared in the superclass (ScopeEntry), otherwise return false.
	 */
	public boolean addBinding(String name, Entry symTabEntry) {
		if (lookup(name) == null) {
			localSymtab.put(name, symTabEntry);
			return true;
		}
		return false;
	}

	/**
	 * Return a String representation of the class.
	 */
	public String toString() {
		String str = "class " + this.name() + " {\n";
		if (localSymtab.size() > 0) {
			for (Map.Entry<String, Entry> entry : localSymtab.entrySet()) {
				str += entry.getValue().toString() + ";\n";
			}
		}
		str += "}\n";
		return str;
	}
} // End of class ClassEntry
