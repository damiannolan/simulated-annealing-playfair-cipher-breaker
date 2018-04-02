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

## Playfair Cipher

The [Playfair cipher](https://en.wikipedia.org/wiki/Playfair_cipher) or Playfair square or Wheatstone-Playfair cipher is a manual symmetric encryption technique and was
the first literal digram substitution cipher. The scheme was invented in 1854 by Charles Wheatstone, but bears the name
oi Lord Playfair for promoting its use. Due to its simplicity, the Playfair Cipher was used at a tactical level by both the British and US forces
during WWII and is also notable for its role in the rescue of the crew of PT-109 in the Pacific
in 1943.

Polygram substitution is a classical system of encryption in which a group of *n* plain-text letters
is replaced as a unit by *n* cipher-text letters. In the simplest case, where *n* = 2, the system is
called digraphic and each letter pair is replaced by a cipher digraph. The Playfair Cipher uses
digraphs to encrypt and decrypt from a 5x5 matrix constructed from a sequence key of 25
unique letters. Note that the letter J is not included. Memorization of the keyword and 4 simple rules was all that was
required to create the 5 by 5 table and use the cipher.

To generate the key table, one would first fill in the spaces in the table with the letters of the keyword (dropping any
duplicate letters), then fill the remaining spaces with the rest of the letters of the alphabet in order (usually
omitting "J" or "Q" to reduce the alphabet to fit; other versions put both "I" and "J" in the same space). The key can
be written in the top rows of the table, from left to right, or in some other pattern, such as a spiral beginning in the
upper-left-hand corner and ending in the center. The keyword together with the conventions for filling in the 5 by 5
table constitute the cipher key.

To encrypt a message, one would break the message into digrams (groups of 2 letters) such that, for example,
"HelloWorld" becomes "HE LL OW OR LD". These digrams will be substituted using the key table. Since encryption requires
pairs of letters, messages with an odd number of characters usually append an uncommon letter, such as "X", to complete
the final digram. The two letters of the digram are considered opposite corners of a rectangle in the key table. To
perform the substitution, apply the following 4 rules, in order, to each pair of letters in the plaintext:

1. If both letters are the same (or only one letter is left), add an "X" after the first letter. Encrypt the new pair and
continue. Some variants of Playfair use "Q" instead of "X", but any letter, itself uncommon as a repeated pair, will do.

2. If the letters appear on the same row of your table, replace them with the letters to their immediate right
   respectively (wrapping around to the left side of the row if a letter in the original pair was on the right side of
the row).

3. If the letters appear on the same column of your table, replace them with the letters immediately below respectively
   (wrapping around to the top side of the column if a letter in the original pair was on the bottom side of the
column).

4. If the letters are not on the same row or column, replace them with the letters on the same row respectively but at
   the other pair of corners of the rectangle defined by the original pair. The order is important – the first letter of
the encrypted pair is the one that lies on the same row as the first letter of the plaintext pair.

To decrypt, use the INVERSE (opposite) of the last 3 rules, and the 1st as-is (dropping any extra "X"s, or "Q"s that do
not make sense in the final message when finished).

NOTE: There are several minor variations of the original Playfair Cipher.

## References

1. [Cryptanalysis of the Playfair Cipher](http://practicalcryptography.com/cryptanalysis/stochastic-searching/cryptanalysis-playfair/)
2. [Quadgram Statistics as a Fitness Measure](http://practicalcryptography.com/cryptanalysis/text-characterisation/quadgrams/)

