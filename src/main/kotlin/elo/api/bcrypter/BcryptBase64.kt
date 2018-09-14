@file:JvmName("BcryptBase64")
package elo.api.bcrypter

import kotlin.experimental.and
import kotlin.experimental.or

object BcryptBase64 {

    private infix fun Byte.shr(bitCount: Int): Byte {
        return (toInt() shr bitCount).toByte()
    }

    private infix fun Byte.shl(bitCount: Int): Byte {
        return (toInt() shl bitCount).toByte()
    }

    // Table for Base64 encoding
    private val BASE64_BCRYPT_ALPHABET = "./ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray()

    @Throws(IllegalArgumentException::class)
    @JvmStatic
    fun encodeBcryptBase64(b: ByteArray, len: Int): String {
        var off = 0
        val rs = StringBuffer()
        var c1: Byte
        var c2: Byte

        if (len <= 0 || len > b.size)
            throw IllegalArgumentException("Invalid len")
        val fullbyte = (0xff).toByte()
        while (off < len) {
            c1 = (b[off++] and fullbyte)
            rs.append(BASE64_BCRYPT_ALPHABET[(c1 shr 2 and 0x3f).toInt()])
            c1 = c1 and 0x03 shl 4
            if (off >= len) {
                rs.append(BASE64_BCRYPT_ALPHABET[(c1 and 0x3f).toInt()])
                break
            }
            c2 = (b[off++] and fullbyte)
            c1 = c1 or ((c2 shr 4) and 0x0f)
            rs.append(BASE64_BCRYPT_ALPHABET[(c1 and 0x3f).toInt()])
            c1 = (c2 and 0x0f) shl 2
            if (off >= len) {
                rs.append(BASE64_BCRYPT_ALPHABET[(c1 and 0x3f).toInt()])
                break
            }
            c2 = b[off++] and fullbyte
            c1 = c1 or ((c2 shr 6) and 0x03)
            rs.append(BASE64_BCRYPT_ALPHABET[(c1 and 0x3f).toInt()])
            rs.append(BASE64_BCRYPT_ALPHABET[(c2 and 0x3f).toInt()])
        }
        return rs.toString()
    }
}