#include "com_laziestcoder_jmbus_JMeterBusService.h"

#include <iostream>
#include <err.h>
#include <stdio.h>
#include <string.h>
#include "mbus/mbus.h"
using namespace std;

extern "C" {

/*
 * Class:     com_laziestcoder_jmbus_JMeterBusService
 * Method:    decodeHexValueFromCLibrary
 * Signature: ()Ljava/lang/String;
 */

/*
* Custom Methods Declarations
*/

string getDecodedHexValue(string hexDataForDecode);
string jstringToString(JNIEnv *env, jstring jStr);


/*
* Main Method
*/

JNIEXPORT jstring JNICALL Java_com_laziestcoder_jmbus_JMeterBusService_decodeHexValueFromCLibrary
(JNIEnv *env, jobject thisObject, jstring hexDataForDecode) {
	string convertedStr = jstringToString(env, hexDataForDecode);
	string result = "";
	result = getDecodedHexValue(convertedStr);
    return env->NewStringUTF(result.c_str());
}

/*
* Custom Method Definitions
*/
string getDecodedHexValue(string hexDataForDecode) {
    size_t buff_len;
    int result, normalized = 0;
    unsigned char raw_buff[4096], buff[4096];
    mbus_frame reply;
    mbus_frame_data frame_data;
    char *xml_result = NULL;

    memset(raw_buff, 0, sizeof(raw_buff));
    strcpy((char*) raw_buff, hexDataForDecode.c_str());

    buff_len = mbus_hex2bin(buff, sizeof(buff), raw_buff, sizeof(raw_buff));

    memset(&reply, 0, sizeof(reply));
    memset(&frame_data, 0, sizeof(frame_data));

    result = mbus_parse(&reply, buff, buff_len);

    if (result < 0)
    {
        cout << "mbus_parse: " << mbus_error_str() << endl;
        return "1";
    }
    else if (result > 0)
    {
        cout << "mbus_parse: need " << result << " more bytes" << endl;
        return "1";
    }

    result = mbus_frame_data_parse(&reply, &frame_data);

    if (result != 0)
    {
        mbus_frame_print(&reply);
        cout << "mbus_frame_data_parse: " << mbus_error_str() << endl;
        return "1";
    }

    //mbus_frame_print(&reply);
    //mbus_frame_data_print(&frame_data);

    xml_result = normalized ? mbus_frame_data_xml_normalized(&frame_data) : mbus_frame_data_xml(&frame_data);

    if (xml_result == NULL)
    {
        fprintf(stderr, "Failed to generate XML representation of MBUS frame: %s\n", mbus_error_str());
        return "1";
    }
//    printf("%s", xml_result);
    free(xml_result);
    mbus_data_record_free(frame_data.data_var.record);

    return result == 0 ? xml_result : to_string(result);
}

string jstringToString(JNIEnv *env, jstring jStr) {
    if (!jStr)
        return "";

    const jclass stringClass = env->GetObjectClass(jStr);
    const jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
    const jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes, env->NewStringUTF("UTF-8"));

    size_t length = (size_t) env->GetArrayLength(stringJbytes);
    jbyte* pBytes = env->GetByteArrayElements(stringJbytes, NULL);

    string result = string((char *)pBytes, length);
    env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

    env->DeleteLocalRef(stringJbytes);
    env->DeleteLocalRef(stringClass);
    return result;
}

}