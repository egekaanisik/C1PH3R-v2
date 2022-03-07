# C1PH3R v2

This is the improved version of my [C1PH3R project](https://github.com/egekaanisik/C1PH3R). It has a better encryption technique that involves multiple lines of keys and a more complicated ASCII change method.

## How to Run

There are 2 options:

1. By running directly run the [Cipher2.java](C1PH3Rv2/src/dev/mrpanda/C1PH3Rv2/Cipher2.java).
2. By creating a runnable JAR file with a starting point of [Cipher2.java](C1PH3Rv2/src/dev/mrpanda/C1PH3Rv2/Cipher2.java).

## Welcome Screen

<p align="center">
<img width="400" src=https://user-images.githubusercontent.com/66966617/157044343-449a283a-d12d-46a1-8efb-21fa86c829d7.png>
</p>

This page involves two large buttons to select the function. Both buttons open the related functions dedicated screen.

## Encryption

Encryption is done in two layers. First layer is getting the related character based on the random key indicator and the key counter inside the algorithm. The second layer is about changing the character based on its ASCII value. The text is finalized after some randomized characters added between the valuable characters.

### Encryption GUI

<p align="center">
<img width="400" src=https://user-images.githubusercontent.com/66966617/157045062-1dff857f-9663-481c-93c7-4934cfba23e5.png>
</p>

The GUI consists of 4 main parts:

- Key Selection
- Plaintext Input Area
- Ciphertext Output Area
- Operation Control

#### Key Selection

The key can be selected via the button above the path text area if there is a key already generated. In order to generate a new key, entering a unique file name in the file selection dialog will be enough.

<p align="center">
<img width="400" src=https://user-images.githubusercontent.com/66966617/157046411-04414b39-8b8a-484c-8bdc-7913a371ab0c.png>
</p>
<p align="center">
<img width="400" src=https://user-images.githubusercontent.com/66966617/157046533-90f3a13d-5b8b-4f04-95c8-247017aa9b2d.png>
</p>

#### Plaintext Input Area

The text to be encrypted is entered to the algorithm via this text area. The text must contain only ASCII characters. If a whole document needs to be encrypted, the "Import" button allows user to select a file via a dialog as well. Please note that only text files (.txt) can be selected. The "Copy" button simply copies the text inside the text area.

#### Ciphertext Output Area

The output of the program can be seen here. As the "Import" button, there is an "Export" button to save the ciphered text into a file. The "Copy" button copies the text as in previous part.

#### Operation Control

This part consists of three elements:

- Status Bar: The bar displays the status message of the program if the operation is successful or not.
- Go Back Button: Goes back to the welcome screen.
- Encrypt Button: Starts the algorithm.

## Decryption

The algorithm checks the last characters of the ciphertext to understand the encryption parameters. After that, it gets the key seed from the next characters from the last part of the text to recreate the key, so the algorithm can check if the given key is the key that the message encrypted with. If all the parameters are correct, the decryption is done by reversing the procedure with the given parameters.

### Decryption GUI

<p align="center">
<img width="400" src=https://user-images.githubusercontent.com/66966617/157050310-62a960c8-17ef-4a06-a786-ae5e5fe09859.png>
</p>

The decryption GUI consists of the same elements with the encryption GUI, but this time, the user cannot create a key or cannot enter a text with spaces into the ciphertext input area. Apart from these, same rules apply for each part.









