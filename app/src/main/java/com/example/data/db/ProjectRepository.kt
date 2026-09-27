package com.example.data.db

import kotlinx.coroutines.flow.Flow

class ProjectRepository(private val projectDao: ProjectDao) {
    val allProjects: Flow<List<ProjectEntity>> = projectDao.getAllProjects()

    suspend fun insert(project: ProjectEntity): Long {
        return projectDao.insertProject(project)
    }

    suspend fun delete(project: ProjectEntity) {
        projectDao.deleteProject(project)
    }

    suspend fun deleteById(id: Long) {
        projectDao.deleteById(id)
    }
}
