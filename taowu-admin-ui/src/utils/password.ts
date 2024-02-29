import { JSEncrypt } from 'jsencrypt';

const publicKey = 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA183EzqDs126Mf7mQmnvZni95tmZIeHImKUJaogYEMXXr2AioadOvGhbZA0ID5xgI1aSoAqoqQrDRvgk0Jur07NybtPVZtSrfMIR45uMcPEaUAuI8kNhmpWYduj6+zteKmTAF96JM0OgtIWe8TYYKF6HL7Fmui7tZwbleVQn9o3BihmXbOckmlAU6UFsCBMU0Dk5LkTMBX/hLpE3Dr1JpIkmOzgO0gKyStbooy6ifDBxy5juSy7VMrc3pEoa/+mSs3ei0WqcArwt2hyySOvnAbCAa9f2TR14eIRTuBEiH+SkAPN0+t5kl1nQNcxy+E3SVwYQF8Za6VzvXDhTo7XuxuQIDAQAB';


const encryptByPublicKey = (token: string) => {
    const encryptor = new JSEncrypt();
    encryptor.setPublicKey(publicKey);
    return encryptor.encrypt(token);
};

const encryptByPublicKey2 = (token: string) => {
    const encryptor = new JSEncrypt();
    encryptor.setPublicKey(publicKey);
    return encryptor.encrypt(token);
};

export {  encryptByPublicKey, encryptByPublicKey2 };
