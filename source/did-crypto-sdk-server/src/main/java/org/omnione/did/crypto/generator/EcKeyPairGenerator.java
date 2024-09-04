/* 
 * Copyright 2024 Raonsecure
 */

package org.omnione.did.crypto.generator;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.omnione.did.crypto.constant.CryptoConstant;
import org.omnione.did.crypto.enums.EccCurveType;
import org.omnione.did.crypto.exception.CryptoErrorCode;
import org.omnione.did.crypto.exception.CryptoException;
import org.omnione.did.crypto.keypair.EcKeyPair;
import org.omnione.did.crypto.keypair.KeyPairInterface;

public class EcKeyPairGenerator {
  
    /**
     * Generates an EC key pair using the specified ECC curve type.
     *
     * @param eccCurveType The type of ECC curve to use
     * @return The generated EC key pair
     * @throws CryptoException
     */
    public KeyPairInterface generateKeyPair(EccCurveType eccCurveType) throws CryptoException {

        EcKeyPair ecKeyPair;

        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance(CryptoConstant.PROVIDER_EC, CryptoConstant.PROVIDER_BC);
            ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec(eccCurveType.getCurveName());
            g.initialize(ecSpec);

            java.security.KeyPair keyPair = g.generateKeyPair();
            ecKeyPair = new EcKeyPair(keyPair.getPublic(), keyPair.getPrivate());
            ecKeyPair.setECType(eccCurveType);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(CryptoErrorCode.ERR_CODE_CRYPTOUTIL_GEN_RANDOM_KEY_FAIL, e.getMessage());
        } catch (NoSuchProviderException e) {
            throw new CryptoException(CryptoErrorCode.ERR_CODE_CRYPTOUTIL_GEN_RANDOM_KEY_FAIL, e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            throw new CryptoException(CryptoErrorCode.ERR_CODE_CRYPTOUTIL_GEN_RANDOM_KEY_FAIL, e.getMessage());
        }
        return ecKeyPair;
    }
}