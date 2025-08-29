export interface Task {
  taskId?: number;
  title: string;
  description?: string;
  projectId?: number;
  status?: string;
  creationDate: string;
}

export interface TaskRequest {
  title: string;
  description?: string;
  projectId?: number;
}

export interface TaskResponse {
  taskId: number;
  title: string;
  description?: string;
  projectId: number;
  status: string;
  creationDate: string;
}
