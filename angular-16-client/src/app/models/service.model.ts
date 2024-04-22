import {ServiceCategoryDTO} from "./service-category.model";

export class ServiceDTO {
  constructor(public id: number,
              public name: string,
              public description: string,
              public price: number,
              public active: boolean,
              public serviceCategory: ServiceCategoryDTO,
              public imageUrl: string) {
  }
}
