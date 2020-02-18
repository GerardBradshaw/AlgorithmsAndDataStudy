package hashtable

data class Word(val word: String, val definition: String) {
  override fun toString(): String {
    return "$word: $definition"
  }
}