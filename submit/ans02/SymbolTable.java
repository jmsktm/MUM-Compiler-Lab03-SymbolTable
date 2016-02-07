import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class SymbolTable {

	/**
	 * Create a new global scope object. Initialize the scope stack so this new
	 * global scope entry is the current scope, i.e., this global scope entry
	 * must be the top entry of the scope stack. Also, note that from now on,
	 * this global scope will always be at the bottom of the stack. In this case
	 * there is only one entry on the scope stack.
	 */
	private Deque<ScopeEntry> scopeStack;
	
	public SymbolTable() {
		scopeStack = new LinkedList<ScopeEntry>();
		scopeStack.addFirst(new GlobalEntry());
	}

	/**
	 * Insert a binding for parameter 'symTabEntry' in the current scope, i.e.,
	 * in the top entry of the scope stack. No ScopeEntry is pushed onto or
	 * popped off the scope stack during this operation. This method only adds a
	 * binding for 'symTabEntry'.
	 */
	public boolean insertBinding(Entry symTabEntry) {
		ScopeEntry top = scopeStack.getLast();
		return top.addBinding(symTabEntry.name(), symTabEntry);
	}

	/**
	 * Return a String representing the current scope, i.e., call toString() on
	 * the top entry.
	 */
	public String currentScope() {
		String str = "";
		if (scopeStack.size() > 0) {
			ScopeEntry entry = scopeStack.getLast();
			str += entry.toString();
			if (entry.getClass() == MethodEntry.class) {
				str += ";\n";
			}
			return str;
		} else {
			return null;
		}
	}

	/**
	 * Lookup 'name' in each ScopeEntry on the stack until found, starting at
	 * the top (the current scope) and ending with the global scope (the entry
	 * at the bottom of the stack). Return null if not found.
	 *
	 * This is done to handle nested scopes, i.e., so the proper binding in the
	 * nearest enclosing scope is returned in cases when the same name denotes
	 * two different entities.
	 */
	public Entry lookup(String name) {
		Iterator<ScopeEntry> it = scopeStack.descendingIterator();
		
		Entry result = null;
		while (it.hasNext()) {
			ScopeEntry entry = it.next();
			result = entry.lookup(name);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Look up the qualified name 'name1.name2'
	 * 
	 * To do this, first look up 'name1' as in the above 'lookup' method. If
	 * found, make sure it is an instance of ClassEntry. If not a ClassEntry (or
	 * null), return null. If it is, then look up name2 in this ClassEntry.
	 * 
	 * Return null if 'name1.name2' is not found.
	 */
	public Entry lookup(String name1, String name2) {
		Iterator<ScopeEntry> it = scopeStack.descendingIterator();
		
		Entry result = null;
		while (it.hasNext()) {
			ScopeEntry entry = it.next();
			result = entry.lookup(name1);
			if (result != null && (result.getClass() == ClassEntry.class)) {
				break;
			}
		}
		
		if (result != null) {
			return ((ClassEntry) result).lookup(name2);
		}
		return null;
	}

	/**
	 * Return the MethodEntry instance nearest the top of the scope stack.
	 * Return null if no such object is found in the scope stack.
	 */
	public MethodEntry enclosingMethod() {
		Iterator<ScopeEntry> it = scopeStack.descendingIterator();
		
		while (it.hasNext()) {
			ScopeEntry entry = it.next();
			if (entry instanceof MethodEntry) {
				return (MethodEntry) entry;
			}
		}
		return null;
	}

	/**
	 * Make the parameter 'scopeEntry' the current scope, i.e., push
	 * 'scopeEntry' onto the scope stack.
	 *
	 * Return true if the operation is successful, otherwise return false.
	 */
	public boolean enterScope(ScopeEntry scopeEntry) {
		if (scopeEntry != null) {
			scopeStack.addLast(scopeEntry);
			return true;
		}
		return false;
	}

	/**
	 * Leave the current scope by popping the current scope entry (the top
	 * entry) off the scope stack. Return the ScopeEntry that is being popped
	 * off the stack.
	 *
	 * Return null if there is an attempt to leave the global scope, that is, do
	 * not allow the bottom entry (the global scope) to be popped off the stack.
	 * Thus, in such cases, return null and do not leave the global scope.
	 */
	public ScopeEntry leaveScope() {
		ScopeEntry top = scopeStack.removeLast();
		return top;
	}

	/**
	 * Create a new instance of 'BlockEntry' and make it the current scope,
	 * i.e., push the newly created 'BlockEntry' onto the top of the scope
	 * stack.
	 *
	 * Return true if the operation is successful, otherwise return false.
	 */
	public boolean enterNewBlock() {
		BlockEntry blockEntry = new BlockEntry();
		enterScope(blockEntry);
		return true;
	}

	/**
	 * Return a String representation of the global scope entry (the entry at
	 * the bottom of scope stack) i.e., call toString() on the bottom entry of
	 * the stack.
	 */
	public String toString() {
		return scopeStack.getFirst().toString();
	}
} // End of class SymbolTable
