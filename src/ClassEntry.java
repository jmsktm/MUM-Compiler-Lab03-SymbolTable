
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
		return false;
	}

	/**
	 * Return a String representation of the class.
	 */
	public String toString() {
		return "class " + this.name() + "{\n" + "}\n";
	}
} // End of class ClassEntry
