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

### Contents
1. [Getting Started](#getting-started)
2. [The Playfair Cipher](#playfair-cipher)
3. [The Simulated Annealing Algorithm](#simulated-annealing)
4. [Using *n*-Gram Statistics as a Heuristic Function](#ngram-stats)
5. [Thoughts and Observations](#thoughts)
6. [References](#references)

<a name="getting-started"></a>
## Getting Started 

Download and Install any missing dependencies from the list below.

### Dependencies
- [Java 8](https://java.com/en/download/)
- [Maven](https://maven.apache.org/)

### Usage

1. Clone this repository
```
git clone https://github.com/damiannolan/simulated-annealing-playfair-cipher-breaker.git
```

2. Build the project from the PlayfairCipherBreaker directory
```
mvn install 
```

3. Run the CLI application
```
java -cp ./target/PlayfairCipherBreaker-0.0.1-SNAPSHOT.jar ie.gmit.sw.ai.CipherBreaker
```

<a name="playfair-cipher"></a>
## The Playfair Cipher

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

<a name="simulated-annealing"></a>
## The Simulated Annealing Algorithm

[Simulated annealing (SA)](https://en.wikipedia.org/wiki/Simulated_annealing) is an excellent approach for breaking a cipher using a randomly
generated key. Unlike conventional Hill Climbing algorithms, that are easily side-tracked by
local optima, SA uses randomization to avoid heuristic plateaux and attempt to find a global
maxima solution. 

*Note:* The initial value of the variables temp and transitions can have a
major impact on the success of the SA algorithm. Both variables control the cooling schedule
of SA and should be experimented with for best results. 

For the purposes of this project the transitions variable is hardcoded at 50,000. However, a user may enter 
a desired starting temperature and play around with how it affects the outcome of the results.

### Simulated Annealing Pseudocode
The following pseudocode shows how simulated annealing can be used break
a Playfair Cipher:

```
1. Generate a random key, called the 'parent', decipher the ciphertext 
    using this key. 
2. Rate the fitness of the deciphered text, store the result.
3. for(TEMP = 10; TEMP >= 0; TEMP = TEMP - STEP)
      for (trans = 50,000; trans > 0; trans--)
         Change the parent key slightly (e.g. swap two characters in the 
           key at random) to get child key, 
         Measure the fitness of the deciphered text using the child key
         set dF = (fitness of child - fitness of parent)
         If dF > 0 (fitness of child is higher than parent key), 
             set parent = child
         If dF < 0 (fitness of child is worse than parent), 
             set parent = child with probability e^(dF/T). 
```

### Key Shuffling
The method `shuffleKey()` on line 6 should make the following changes to the key with the
frequency given (you can approximate this using `Math.random() * 100`):

- Swap single letters (90%)
- Swap random rows (2%)
- Swap columns (2%)
- Flip all rows (2%)
- Flip all columns (2%)
- Reverse the whole key (2%)

<a name="ngram-stats"></a>
## Using *n*-Gram Statistics as a Heuristic Function
An n-gram (gram = word or letter) is a substring of a word(s) of length n and can be used to
measure how similar some decrypted text is to English. For example, the quadgrams (4-grams)
of the word "HAPPYDAYS" are "HAPP", "APPY", "PPYD", "PYDA", "YDAY" and
"DAYS". A fitness measure or heuristic score can be computed from the frequency of
occurrence of a 4-gram, q, as follows: P(*q*) = count(*q*) / *n*, where *n* is the total number of 4-
grams from a large sample source. An overall probability of the phrase "HAPPYDAYS" can
be accomplished by multiplying the probability of each of its 4-grams:

```
P(HAPPYDAYS) = P(HAPP) × P(APPY) × P(PPYD) × P(PYDA) × P(YDAY)
```

One side effect of multiplying probabilities with very small floating point values is that
underflow can occur if the exponent becomes too low to be represented. For example, a Java
**_float_** is a 32-bit IEEE 754 type with a 1-bit sign, an 8-bit exponent and a 23-bit mantissa. The
64-bit IEEE 754 __*double*__ has a 1-bit sign, a 11-bit exponent and a 52-bit mantissa. A simple way
of avoiding this is to get the log (usually base 10) of the probability and use the identity log(a
× b) = log(a) + log(b). Thus, the score, h(n), for “HAPPYDAYS” can be computed as a log
probability:

```
log10(P(HAPPYDAYS)) = log10(P(HAPP)) + log10(P(APPY)) + log10(P(PPYD)) + log10(P(PYDA)) + log10(P(YDAY)
```

The resource **_quadgrams.txt_** is a text file containing a total of 389,373 4-grams, from a
maximum possible number of 26<sup>4</sup>=456,976. The 4-grams and the count of their occurrence
were computed by sampling a set of text documents containing an aggregate total of
4,224,127,912 4-grams. The top 10 4-grams and their occurrence is tabulated below:

| Quadgram-*q* | Count(*q*) | Quadgram-*q* | Count(*q*) |
| ------------ | ---------- | ------------ | ---------- |
| TION         | 13168375   | FTHE         | 8100836    |
| NTHE         | 11234972   | THES         | 7717675    |
| THER         | 10218035   | WITH         | 7627991    |
| THAT         | 8980536    | INTH         | 7261789    |
| OFTH         | 8132597    | ATIO         | 7104943    |

The 4-grams of “HAPPYDAYS”, their count, probability and log value are tabulated below.

| Quadgram-*q* | Count(*q*) | Probability(*q*) | Log<sub>10</sub>(P(*q*)) |
| ------------ | ---------- | ---------------- | ------------------------ |
| HAPP         | 462336     | 0.000109451      | -3.960779349             |
| APPY         | 116946     | 0.000027685      | -4.557751689             |
| PPYD         | 4580       | 0.000001084      | -5.964871583             |
| PYDA         | 1439       | 0.000000340      | -6.467676267             |
| YDAY         | 108338     | 0.000025647      | -4.590956247             |
| DAYS         | 635317     | 0.000150401      | -3.822746584             |

The final score, **_h(n)_**, for "HAPPYDAYS" is just the sum of the log probabilities, i.e. -
3.960779349 + -4.557751689 + -5.964871583 + -6.467676267 + -4.590956247 + -
3.822746584 = **-29.36478172**. A decrypted message with a larger score than this is more
"English" than this text and therefore must have been decrypted with a “better” key.

<a name="thoughts"></a>
## Thoughts and Observations

- The use of the Java 8 Streams API makes for a cleaner and in my opinion much more readable code base.
- Java 8 Streams API also offers a slight increase in speed of certain functions.
- The speed of the simulated annealing algorithm is effected largely by the temperature used.
- The effectiveness of the auto generated cipher key (parentKey) may have a massive impact on how quick the decryption
  process takes.

From my experimentation throughout development I've noticed that the application performs much better on pieces of text
250 - 1000 characters in length (or somewhere around that mark). Included in the resources are three sample texts I have
used throughout the project. These texts have been tested and run through the Simulated Annealing decryptor a number of
times and I've come to find that not much more improvements can be made on their scores.

The `logProbability()` scores are tabulated below as follows:

| Text                  | Score       |
| --------------------- | -----------:|
| exam_tips.txt         | -3259.30602 |
| sample.txt            | -1597.00717 |
| thehobbit_excerpt.txt | -4923.32724 |

<a name="references"></a>
## References

Surprisingly, [Wikipedia](https://en.wikipedia.org/wiki/Main_Page) offers good descriptions of many of the
different techniques discussed throughout this project and can be valuable to beginners looking to get an insight into
these fields.

Other helpful resources include:

1. [Cryptanalysis of the Playfair Cipher](http://practicalcryptography.com/cryptanalysis/stochastic-searching/cryptanalysis-playfair/)
2. [Quadgram Statistics as a Fitness Measure](http://practicalcryptography.com/cryptanalysis/text-characterisation/quadgrams/)
3. [The Playfair Cipher](http://practicalcryptography.com/ciphers/classical-era/playfair/)
4. [The Simulated Annealing Algorithm](http://katrinaeg.com/simulated-annealing.html)

