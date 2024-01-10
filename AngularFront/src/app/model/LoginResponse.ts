export interface LoginResponse {
  token?: string;
  type?: string;
  id?: string;
  username?: string;
  email?: string;
  name?: string;
  lastname?: string;
  roles?: string[];
}
