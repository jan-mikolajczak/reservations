import {ServiceDTO} from "./service.model";

export class ServiceCategoryDTO {
  constructor(public id: number,
              public categoryName: string,
              public description: string,
              public active: boolean,
              public service: ServiceDTO) {
  }
}
