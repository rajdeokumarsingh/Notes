key pair 
    private key 
    corresponding public key

1. Generate key pair using KeyPairGenerator
    Algorithm
        for example, Digital Signature Algorithm (DSA)

    steps
        1. get a key-pair generator object for generating keys for the DSA signature algorithm
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            // 参数是算法名和provider名字

        2. initialize the key-pair generator
            key length (in bits)
            source of randomness (SecureRandom)

        3. Generate the Pair of Keys 


2. Sign the Data

    Signature class is used for:
        creating a digital signature 
        verifying a digital signature


    Signing data
        generating a digital signature for that data

        1. Get a Signature Object
            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN"); 

        2. Using private key for signature
            dsa.initSign(mPrivateKey);

        3. Feed data for Signature
            dsa.update(buffer, 0, len);

        4. Generate signature for the data
            dsa.sign();







