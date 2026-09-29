import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Organization } from '../domain/Organization';

@Injectable({
  providedIn: 'root',
})
export class OrganizationService {
  private API_AUTH_URL = 'http://localhost:8080/auth';
  private API_ORGANIZATION_URL = 'http://localhost:8080/api/organizations';

  constructor(private http: HttpClient) {}

  addOrganization(organization: Organization): Observable<Organization> {
    const body = organization;
    return this.http.post<Organization>(
      this.API_AUTH_URL + '/register/organization',
      body,
    );
  }

  getOrganizationByEmail(email: string): Observable<Organization> {
    return this.http.get<Organization>(
      this.API_ORGANIZATION_URL + '/search/' + encodeURIComponent(email),
    );
  }

  updateOrganization(organizationData: any): Observable<any> {
    return this.http.put(
      this.API_ORGANIZATION_URL + '/update',
      organizationData,
    );
  }
}
