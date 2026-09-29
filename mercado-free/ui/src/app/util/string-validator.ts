export class StringValidator {
    static startsWithNumber(value: string): boolean {
      return /^\d/.test(value);
    }
  
    static hasOnlyLettersNumbersSpaces(value: string): boolean {
      return /^[a-zA-Z0-9 ]*$/.test(value);
    }
  
    static validateStringLength(value: string, min: number, max: number): boolean {
      return value.length >= min && value.length <= max;
    }
  
    static isNotEmpty(value: string): boolean {
      return value.trim() !== '';
    }
  }
  