package questions

class S04TreesAndGraphs2 {

  fun buildOrder(projects: ArrayList<String>, dependencies: ArrayList<Array<String>>): ArrayList<String>? {
    if (dependencies.isEmpty()) return projects

    val graph = constructGraph(projects, dependencies)
    var buildableProjects = graph.getBuildableProjects()
    val results = ArrayList<String>(projects.size)

    while (buildableProjects.isNotEmpty()) {
      for (bp in buildableProjects) {
        results.add(bp)
        graph.removeProject(bp)
      }
      buildableProjects = graph.getBuildableProjects()
    }
    return if (results.size != projects.size) null else results
  }

  private fun constructGraph(projects: ArrayList<String>, dependencies: ArrayList<Array<String>>): ProjectGraph {
    val graph = ProjectGraph()
    for (projectName in projects) graph.addProject(projectName)

    for (dependency in dependencies) {
      val parentName = dependency[0]
      val dependentName = dependency[1]
      graph.addDependency(parentName, dependentName)
    }
    return graph
  }

  private class ProjectGraph() {
    private val nameToProjectMap = HashMap<String, Project>()

    fun addProject(name: String) {
      nameToProjectMap[name] = Project(name)
    }

    fun removeProject(name: String) {
      val project = nameToProjectMap[name] ?: return
      val dependents = project.dependents
      for (dependent in dependents) dependent.dependencies--
      nameToProjectMap.remove(name)
    }

    fun getBuildableProjects(): ArrayList<String> {
      val result = ArrayList<String>()
      for (project in nameToProjectMap.values) {
        if (project.dependencies == 0) result.add(project.name)
      }
      return result
    }

    fun addDependency(parent: String, dependent: String) {
      if (!nameToProjectMap.contains(parent)) addProject(parent)
      val parentProject = nameToProjectMap[parent]!!

      if (!nameToProjectMap.contains(dependent)) addProject(dependent)
      val dependentProject = nameToProjectMap[dependent]!!

      dependentProject.dependencies++
      parentProject.dependents.add(dependentProject)
    }

    data class Project(val name: String, val dependents: ArrayList<Project> = ArrayList(), var dependencies: Int = 0)
  }
}