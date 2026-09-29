import { Product } from './product';

describe('Product', () => {
  it('should create an instance', () => {
    const id = 1;
    const name = "name";
    const description = "description";
    const price = 1;
    const brand = "brand";
    const image = "image";
    expect(new Product(id, name, description, price, brand, image)).toBeTruthy();
  });
});
