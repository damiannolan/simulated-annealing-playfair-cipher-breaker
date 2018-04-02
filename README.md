# Using Simulated Annealing to Break a Playfair Cipher

## Overview

The field of [*cryptanalysis*](https://en.wikipedia.org/wiki/Cryptanalysis) is concerned with the study of [ciphers](https://en.wikipedia.org/wiki/Cipher),
 having as its objective the identification of weaknesses within a cryptographic system that may be exploited to convert
encrypted data (cipher-text) into unencrypted data (plain-text). Whether using symmetric or
asymmetric techniques, cryptanalysis assumes no knowledge of the correct cryptographic key
or even the cryptographic algorithm being used.

Assuming that the cryptographic algorithm is known, a common approach for breaking a cipher
is to generate a large number of keys, decrypt a cipher-text with each key and then examine the
resultant plain-text. If the text looks similar to English, then the chances are that the key is a
good one. The similarity of a given piece of text to English can be computed by breaking the
text into fixed-length substrings, called *n*-grams, and then comparing each substring to an
existing map of *n*-grams and their frequency. This process does not guarantee that the outputted
answer will be the correct plain-text, but can give a good approximation that may well be the
right answer.

## References

1. [Cryptanalysis of the Playfair Cipher](http://practicalcryptography.com/cryptanalysis/stochastic-searching/cryptanalysis-playfair/)
2. [Quadgram Statistics as a Fitness Measure](http://practicalcryptography.com/cryptanalysis/text-characterisation/quadgrams/)

