
//------------------------------------------------------------------------------//
//                                   基本参数配置                               //
//------------------------------------------------------------------------------//

#define BIT0 0x01
#define BIT1 0x02
#define BIT2 0x04
#define BIT3 0x08
#define BIT4 0x10
#define BIT5 0x20
#define BIT6 0x40
#define BIT7 0x80

#define SLIC_RC0      (PB_ODR &= ~BIT2)                                          //SLIC的D0端口设置为低
#define SLIC_RC1      (PB_ODR |= BIT2)                                           //SLIC的D0端口设置为高
#define SLIC_FR0      (PB_ODR &= ~BIT0)                                          //SLIC的D1端口设置为低
#define SLIC_FR1      (PB_ODR |= BIT0)                                           //SLIC的D1端口设置为高
#define SLIC_RST0     (PB_ODR &= ~BIT1)                                          //SLIC的D2端口设置为低
#define SLIC_RST1     (PB_ODR |= BIT1)                                           //SLIC的D2端口设置为高
#define HAND_ON       (PB_IDR&BIT5)
#define LINE_ON       (PB_IDR&BIT4)
#define BIT_DET       (BIT3) 
#define SLIC_DET      (PB_IDR&BIT3)

#define LIGHT1_1      (PD_ODR &= ~BIT7)                                          //指示灯1关闭      
#define LIGHT1_0      (PD_ODR |= BIT7)                                           //指示灯1打开
#define LIGHT2_1      (PD_ODR &= ~BIT4)                                          //指示灯2关闭
#define LIGHT2_0      (PD_ODR |= BIT4)                                           //指示灯2打开
#define LIGHT3_1      (PD_ODR &= ~BIT3)                                          //指示灯2关闭
#define LIGHT3_0      (PD_ODR |= BIT3)                                           //指示灯3打开
#define MG_POW1       (PD_ODR &= ~BIT2)                                          //MC55的开启IGT端口设置为高
#define MG_POW0       (PD_ODR |= BIT2)                                           //MC55的开启IGT端口设置为低
#define MG_VMSM       (PD_IDR&BIT0)                                          //MC55的急停EMERGEOFF端口设置为高

#define CSN_0         (PC_ODR &= ~BIT1)                                          //CMX605的CSN端口设置为低
#define CSN_1         (PC_ODR |= BIT1)                                           //CMX605的CSN端口设置为高
#define SCLK_0        (PC_ODR &= ~BIT4)                                          //CMX605的SCLK端口设置为低
#define SCLK_1        (PC_ODR |= BIT4)                                           //CMX605的SCLK端口设置为高
#define CDATA_0       (PC_ODR &= ~BIT3)                                          //CMX605的CDATA端口设置为低
#define CDATA_1       (PC_ODR |= BIT3)                                           //CMX605的CDATA端口设置为高
#define RDATA         (PC_IDR&BIT2)                                               //CMX605的RDATA端口读入                                    
#define HOST_TO_IN    (PC_ODR &= ~BIT7)                                          //电话线连接到SLIC
#define HOST_TO_SLIC  (PC_ODR |= BIT7)                                           //电话线连接到HOST
#define LINE_TO_TEL   (PC_ODR &= ~BIT5)                                          //电话线连接到SLIC
#define LINE_TO_IN    (PC_ODR |= BIT5)                                           //电话线连接到HOST
#define OUT_EN        (PC_ODR &= ~BIT6)
#define OUT_NC        (PC_ODR |=  BIT6)

#define IRQ           (PE_IDR&BIT5)                                             //CMX605的IRQ端口读入
#define USB_IN        (PF_IDR&BIT4)

#define adress_base   0x4000                                                    //flash地址 
#define adress_0      adress_base+0x000                                         //用于标志Flash是否已经被初始化 08.4.19
#define adress_1      adress_base+0x020                                         //用于标志Flash是否已经被初始化 08.4.19
#define adress_2      adress_base+0x040                                         //用于标志Flash是否已经被初始化 08.4.19
#define adress_3      adress_base+0x060                                         //用于标志Flash是否已经被初始化 08.4.19
#define adress_4      adress_base+0x080                                         //用于标志Flash是否已经被初始化 08.4.19
#define adress_5      adress_base+0x0a0                                         //用于标志Flash是否已经被初始化 08.4.19
#define adress_6      adress_base+0x0c0                                         //用于标志Flash是否已经被初始化 08.4.19
#define adress_7      adress_base+0x0e0                                         //用于标志Flash是否已经被初始化 08.4.19

#define LINEON_TIME   110
#define MAXRESEND     2                                                         //GPRS最大重发次数
#define BPOLY         0x1b                                                      //!< Lower 8 bits of (x^8+x^4+x^3+x+1), ie. (x^4+x^3+x+1).
#define BLOCKSIZE     16                                                        //!< Block size in number of BYTEs.
#define KEYBITS       128                                                       //!< Use AES128.
#define ROUNDS        10                                                        //!< Number of rounds.
#define KEYLENGTH     16                                                        //!< Key length in number of BYTEs.
#define CPRP_ID       0x17  //G221                                                    //公司代号
#define EEPMASS1      0xAE                                                      //密码钥匙1 
#define EEPMASS2      0x56                                                      //密码钥匙2 
typedef unsigned int  WORD;                                                     //定义WORD无符号整型
typedef unsigned char BYTE;                                                     //定义BYTE无符号字节型
typedef unsigned int  U16;                                                      //定义WORD无符号整型
typedef unsigned char U8;     
//------------------------------------------------------------------------------//
//                                  固定字符区                                  //
//------------------------------------------------------------------------------//
//const BYTE FlashCheck[8] = "frontec2";                                          //初始写flash标志
const BYTE  sBox[256] = {                                                       //正向sBox表格   
0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76,   
 0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0,   
 0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15,   
 0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75,   
 0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84,   
 0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf,   
 0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8,   
 0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2,   
 0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73,   
 0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb,   
 0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79,   
 0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08,   
 0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a,   
 0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e,   
 0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf,   
 0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16};   
 
const BYTE  sBoxInv[256] = {                                                    //反向sBox表格    
 0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb,   
 0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb,   
 0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e,   
 0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25,   
 0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92,   
 0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84,   
 0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06,   
 0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b,   
 0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73,   
 0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e,   
 0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b,   
 0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4,   
 0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f,   
 0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef,   
 0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61,   
 0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d
};   

/*
 BYTE AEScode[16]={0x00,0x11,0x22,0x33,0x44,0x55,0x66,0x77,
                       0x88,0x99,0xAA,0xBB,0xCC,0xDD,0xEE,0xFF};
BYTE AESdecode[16]; 
BYTE  key1[16]={0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,                          //密钥
              0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f};
*/
//------------------------------------------------------------------------------//
//                                  全局变量定义                                //
//------------------------------------------------------------------------------//
BYTE try_n,try_n1,try_n2,try_n3;
BYTE LineEN_flag=0;
BYTE LineNC_flag=0;
BYTE Busy_time=0;//2011.11.22
BYTE Connect_gsm=0;//2012.6.13是否注册网络
BYTE LIGHT_flag=0;
BYTE CONTROL=0;
BYTE Line_ENNC=50;//100为可触发；200为可恢复
BYTE ConLine_flag=0;
BYTE Reconnect_flag=0;                                                          //2010.8.5 重连标志
BYTE First_alarm=0;                                                             //第一次报警子学习           
BYTE quality_temp=0;                                                            //临时信号质量记录记录12.30
BYTE USBIN_flag=0;                                                             //闪速标志位１
BYTE Light_flag3=0;                                                             //闪速标志位3
BYTE AT_item=0;                                                                 //AT命令返回状态
//WORD try_time=0;                                                                //AT命令发送尝试次数
BYTE quality=0;                                                                 //信号质量　
BYTE quality_flag=0;                                                            //信号质量强度　
BYTE GPRS_flag=0;                                                               //GPRS连接标志                    　　　　　　　　　　　　　　　　　　　　　　　
BYTE start_end=0;                                                               //命令开始接受标志　　　　　　　　　　　　
BYTE restart_time=0;                                                            //GPRS重新启动标志
BYTE CMX_Receive=0;                                                             //CMX接受寄存器　　　　　　　　　　　　　　　　
BYTE Call_flag=0;                                                               //报警主机拨号标志
BYTE GPRS_Send_flag=0;                                                          //gprs发送标志位
BYTE SendData_Longth=0;                                                         //gprs发送数据长度                                                    
BYTE Socket_Reflag=0;                                                           //gprs接收read标志
BYTE AT_flag=0;                                                                 //AT命令标志
BYTE CID_flag=0;                                                                //主机握手音标志
BYTE register_time=0;                                                           //注册时间

BYTE temp_m[3];                                                                 //零时    ２ 
BYTE ACCT[8];                                                                   //特殊关键控制寄存器
BYTE key[16]={0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,                          //密钥
              0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f};
BYTE phone_number[5];                                                          //电话号码
BYTE ID_message[16];                                                            //CID码
BYTE temp_DTMF[16];                                                             //CMX接受缓存
BYTE AT_data[3][48];                                                            //at命令数据
BYTE Socket_data[48];                                                           //gprs接受数据
BYTE light_flag=0;
//------------------------------------------------------------------------------//
//                                   结构体定义                                 //
//------------------------------------------------------------------------------//
typedef struct                                                                  //定义SENDBUFFER(缓存)结构体
{
  BYTE longth;                                                                  //包含字长
  BYTE data[64];                                                                //包含数据内容
} BUFFER;
BUFFER SendBuffer;                                                              //申明SendBuffer
BUFFER ReceiveBuffer;                                                           //申明ReceiveBuffer                                                            //申明ReceiveBuffer
BUFFER USBSeBuffer;
BUFFER USBReBuffer;

typedef struct                                                                  //定义HTTPADDRESS(ip地址)结构体
{
  BYTE longth;                                                                  //包含address的长度　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　　
  BYTE address[28];                                                             //包含address数据内容
  BYTE port[4];
} HTTPADDRESS;
HTTPADDRESS httpaddress1;                                                       //申明httpaddress1　　　　　　　　　　　　　　　
HTTPADDRESS httpaddress2;                                                       //申明httpaddress2
BYTE *expandedKey;                                                              //AES加密用 
BYTE *Flash_ptr;    //flash指针
//------------------------------------------------------------------------------//
//                                   子函数区                                   //
//------------------------------------------------------------------------------//
//******************************************************************************//
void CycleLeft( BYTE * row )                                                    //加密函数
{
    BYTE  temp = row[0];                                                        // Cycle 4 BYTEs in an array left once.
    row[0] = row[1];
    row[1] = row[2];
    row[2] = row[3];
    row[3] = temp;
}

//******************************************************************************//
void InvMixColumn( BYTE * column )                                              //加密函数
{
    BYTE  r0, r1, r2, r3;
    r0 = column[1] ^ column[2] ^ column[3];
    r1 = column[0] ^ column[2] ^ column[3];
    r2 = column[0] ^ column[1] ^ column[3];
    r3 = column[0] ^ column[1] ^ column[2];
     column[0] = (column[0] << 1) ^ (column[0] & 0x80 ? BPOLY : 0);
     column[1] = (column[1] << 1) ^ (column[1] & 0x80 ? BPOLY : 0);
     column[2] = (column[2] << 1) ^ (column[2] & 0x80 ? BPOLY : 0);
     column[3] = (column[3] << 1) ^ (column[3] & 0x80 ? BPOLY : 0);
    r0 ^= column[0] ^ column[1];
    r1 ^= column[1] ^ column[2];
    r2 ^= column[2] ^ column[3];
    r3 ^= column[0] ^ column[3];
       column[0] = (column[0] << 1) ^ (column[0] & 0x80 ? BPOLY : 0);
       column[1] = (column[1] << 1) ^ (column[1] & 0x80 ? BPOLY : 0);
       column[2] = (column[2] << 1) ^ (column[2] & 0x80 ? BPOLY : 0);
       column[3] = (column[3] << 1) ^ (column[3] & 0x80 ? BPOLY : 0);
    r0 ^= column[0] ^ column[2];
    r1 ^= column[1] ^ column[3];
    r2 ^= column[0] ^ column[2];
    r3 ^= column[1] ^ column[3];
       column[0] = (column[0] << 1) ^ (column[0] & 0x80 ? BPOLY : 0);
       column[1] = (column[1] << 1) ^ (column[1] & 0x80 ? BPOLY : 0);
       column[2] = (column[2] << 1) ^ (column[2] & 0x80 ? BPOLY : 0);
       column[3] = (column[3] << 1) ^ (column[3] & 0x80 ? BPOLY : 0);
    column[0] ^= column[1] ^ column[2] ^ column[3];
    r0 ^= column[0];
    r1 ^= column[0];
    r2 ^= column[0];
    r3 ^= column[0];
    column[0] = r0;
    column[1] = r1;
    column[2] = r2;
    column[3] = r3;
}

//******************************************************************************//
BYTE Multiply( BYTE num, BYTE factor )                                          //加密函数 
{
  BYTE mask = 1;
  BYTE result = 0;
  while( mask != 0 ) 
  {                                                                             // Check bit of factor given by mask.
    if( mask & factor ) 
    {                                                                           // Add current multiple of num in GF(2).
      result ^= num;
    }
    mask <<= 1;                                                                 // Shift mask to indicate next bit.
    num = (num << 1) ^ (num & 0x80 ? BPOLY : 0);                                // Double num.
  }
  return result;
}

//******************************************************************************//
BYTE DotProduct( BYTE * vector1, BYTE * vector2 )                               //加密函数
{
    BYTE result = 0;
    result ^= Multiply( *vector1++, *vector2++ );
    result ^= Multiply( *vector1++, *vector2++ );
    result ^= Multiply( *vector1++, *vector2++ );
    result ^= Multiply( *vector1 , *vector2   );
    return result;
}

//******************************************************************************//
void MixColumn( BYTE * column )                                                 //加密函数
{
    BYTE  row[8] = {0x02, 0x03, 0x01, 0x01,0x02, 0x03, 0x01, 0x01};             // Prepare first row of matrix twice, to eliminate need for cycling.
    BYTE  result[4];
    result[0] = DotProduct( row+0, column );                                    // Take dot products of each matrix row and the column vector.
    result[1] = DotProduct( row+3, column );
    result[2] = DotProduct( row+2, column );
    result[3] = DotProduct( row+1, column ); 
    column[0] = result[0];                                                      // Copy temporary result to original column.
    column[1] = result[1];
    column[2] = result[2];
    column[3] = result[3];
}

//******************************************************************************//
void SubBytes( BYTE * BYTEs, BYTE count )                                       //加密函数
{
  do 
  {
    *BYTEs = sBox[*BYTEs];                                                      // Substitute every BYTE in state.
    BYTEs++;
  } 
  while( --count );
}

//******************************************************************************//
void InvSubBytesAndXOR( BYTE * BYTEs, BYTE * key, BYTE count )                  //加密函数
{ 
  do 
  {
    *BYTEs = sBoxInv[ *BYTEs ] ^ *key;                                          // Use block2 directly. Increases speed.
    BYTEs++;
    key++;
  } 
  while( --count );
}

//******************************************************************************//
void InvShiftRows( BYTE * state )                                               //加密函数
{
    BYTE temp;
    temp = state[ 1 + 3*4 ];                                                    // Note: State is arranged column by column.
    state[ 1 + 3*4 ] = state[ 1 + 2*4 ];                                        // Cycle second row right one time.
    state[ 1 + 2*4 ] = state[ 1 + 1*4 ];
    state[ 1 + 1*4 ] = state[ 1 + 0*4 ];
    state[ 1 + 0*4 ] = temp;
    temp = state[ 2 + 0*4 ];                                                    // Cycle third row right two times.
    state[ 2 + 0*4 ] = state[ 2 + 2*4 ];
    state[ 2 + 2*4 ] = temp;
    temp = state[ 2 + 1*4 ];
    state[ 2 + 1*4 ] = state[ 2 + 3*4 ];
    state[ 2 + 3*4 ] = temp;
    temp = state[ 3 + 0*4 ];                                                    // Cycle fourth row right three times, ie. left once.
    state[ 3 + 0*4 ] = state[ 3 + 1*4 ];
    state[ 3 + 1*4 ] = state[ 3 + 2*4 ];
    state[ 3 + 2*4 ] = state[ 3 + 3*4 ];
    state[ 3 + 3*4 ] = temp;
}

//******************************************************************************//
void ShiftRows( BYTE * state )                                                  //加密函数
{
    BYTE temp;
    temp = state[ 1 + 0*4 ];                                                    // Note: State is arranged column by column.
    state[ 1 + 0*4 ] = state[ 1 + 1*4 ];                                        // Cycle second row left one time.
    state[ 1 + 1*4 ] = state[ 1 + 2*4 ];
    state[ 1 + 2*4 ] = state[ 1 + 3*4 ];
    state[ 1 + 3*4 ] = temp;
    temp = state[ 2 + 0*4 ];                                                    // Cycle third row left two times.
    state[ 2 + 0*4 ] = state[ 2 + 2*4 ];
    state[ 2 + 2*4 ] = temp;
    temp = state[ 2 + 1*4 ];
    state[ 2 + 1*4 ] = state[ 2 + 3*4 ];
    state[ 2 + 3*4 ] = temp;
    temp = state[ 3 + 3*4 ];                                                    // Cycle fourth row left three times, ie. right once.
    state[ 3 + 3*4 ] = state[ 3 + 2*4 ];
    state[ 3 + 2*4 ] = state[ 3 + 1*4 ];
    state[ 3 + 1*4 ] = state[ 3 + 0*4 ];
    state[ 3 + 0*4 ] = temp;
}

//******************************************************************************//
void InvMixColumns( BYTE * state )                                              //加密函数
{
    InvMixColumn( state + 0*4 );
    InvMixColumn( state + 1*4 );
    InvMixColumn( state + 2*4 );
    InvMixColumn( state + 3*4 );
}

//******************************************************************************//
void MixColumns( BYTE * state )                                                 //加密函数
{
    MixColumn( state + 0*4 );
    MixColumn( state + 1*4 );
    MixColumn( state + 2*4 );
    MixColumn( state + 3*4 );
}

//******************************************************************************//
void XORBytes( BYTE * BYTEs1, BYTE * BYTEs2, BYTE count )                       //加密函数
{
  do 
  {
     *BYTEs1 ^= *BYTEs2;                                                        // Add in GF(2), ie. XOR.
     BYTEs1++;
     BYTEs2++;
   } 
  while( --count );
}

//******************************************************************************//
void CopyBytes( BYTE * to, BYTE * from, BYTE count )                            //加密函数
{
  do 
  {
    *to = *from;
    to++;
    from++;
  } 
  while( --count );
}

//******************************************************************************//
void KeyExpansion( BYTE *key, BYTE * expandedKey )                              //加密函数
{
    BYTE  temp[4];
    BYTE i;
    BYTE  Rcon[4] = { 0x01, 0x00, 0x00, 0x00 };                                 // Round constant.
    i = KEYLENGTH;                                                              // Copy key to start of expanded key.
    do 
    {
      *expandedKey = *key;
      expandedKey++;
      key++;
    }
    while( --i );
    expandedKey -= 4;                                                           // Prepare last 4 BYTEs of key in temp.
    temp[0] = *(expandedKey++);
    temp[1] = *(expandedKey++);
    temp[2] = *(expandedKey++);
    temp[3] = *(expandedKey++);    
    i = KEYLENGTH;                                                              // Expand key.
    while( i < BLOCKSIZE*(ROUNDS+1) ) 
    {
      if( (i % KEYLENGTH) == 0 )                                                // Are we at the start of a multiple of the key size?
      {                                         
         CycleLeft( temp );                                                     // Cycle left once.
         SubBytes( temp, 4 );                                                   // Substitute each BYTE.
         XORBytes( temp, Rcon, 4 );                                             // Add constant in GF(2).
         *Rcon = (*Rcon << 1) ^ (*Rcon & 0x80 ? BPOLY : 0);
      }  
      #if KEYLENGTH > 24                                                        // Keysize larger than 24 BYTEs, ie. larger that 192 bits?
        
      else if( (i % KEYLENGTH) == BLOCKSIZE ) 
      {                                                                         // Are we right past a block size?
         SubBytes( temp, 4 );                                                   // Substitute each BYTE.
      }
      #endif 
      XORBytes( temp, expandedKey - KEYLENGTH, 4 );                             // Add BYTEs in GF(2) one KEYLENGTH away.
      *(expandedKey++) = temp[ 0 ];                                             // Copy result to current 4 BYTEs.
      *(expandedKey++) = temp[ 1 ];
      *(expandedKey++) = temp[ 2 ];
      *(expandedKey++) = temp[ 3 ];
      i += 4;                                                                   // Next 4 BYTEs.
    }    
}

//******************************************************************************//
void InvCipher( BYTE * in, BYTE * expandedKey ,BYTE  *block)                    //加密函数
{
  BYTE round = ROUNDS-1;
  CopyBytes(block,in,16 );
  expandedKey += BLOCKSIZE * ROUNDS;
  XORBytes( block, expandedKey, 16 );
  expandedKey -= BLOCKSIZE;
  do
  {
    InvShiftRows( block );
    InvSubBytesAndXOR( block, expandedKey, 16 );
    expandedKey -= BLOCKSIZE;
    InvMixColumns( block );
  } 
  while( --round );
  InvShiftRows( block );
  InvSubBytesAndXOR( block, expandedKey, 16 );
}

//******************************************************************************//
void Cipher( BYTE * in, BYTE * expandedKey ,BYTE  *block)                       //加密函数   
{                                                                               //block就是要加密的数据。 expandedkey为扩展后的key ，完成一个块(16字节，128bit)的加密
  BYTE round = ROUNDS-1;
  CopyBytes(block,in,16 );
  XORBytes( block, expandedKey, 16 );
  expandedKey += BLOCKSIZE;
  do 
  {
    SubBytes( block, 16 );
    ShiftRows( block );
    MixColumns( block );
    XORBytes( block, expandedKey, 16 );
    expandedKey += BLOCKSIZE;
  } 
  while( --round );
  SubBytes( block, 16 );
  ShiftRows( block );
  XORBytes( block, expandedKey, 16 );
}

//******************************************************************************//
void aesDecrypt( BYTE *in,BYTE *key,BYTE *out)                                 //加密函数 
{                                                                               //对一个16字节块解密,参数buffer是解密密缓存，chainBlock是要解密的块
  //BYTE out1[16]={0};
  //BYTE key1[16] = {0x20,0x37,0x62,0x39,0x31,0x31,0x31,0x37,0x63,0x30,0x30,0x32,0x31,0x38,0x33,0x33};
  //BYTE in1[16]={0x5A,0x29,0x1C,0x6E,0x41,0xC0,0xAE,0x3E,0x7D,0xD4,0xBA,0xC0,0x2F,0x69,0x3F,0xF6};
  //printf("12345");
  //printf("%8x",in1[0]);
  //for(int p=0;p<16;p++)
	//printf("%8x",in[p]);
  //printf("-----");

  
  //static char name[10] = "123456";
  //strcpy(name,"123456789");
  
  //printf(in1);
  //printf(key1);
  //printf(out1);
  
  //BYTE *in = in1;
  //BYTE *key = key1;
  //BYTE *out = out1;
  
  //printf(in);
  //printf("/n");
  //printf(key);
  //printf("/n");
  //printf(in1);
  
  /*BYTE space[ 256 ];                                                            //AES加密用
  expandedKey =space;                                                           //至此space用来存贮密码表
  KeyExpansion(key, expandedKey );
  InvCipher(in, expandedKey ,out);
*/  
   BYTE space[ 256 ];                                                            //AES加密用
  expandedKey =space;                                                           //至此space用来存贮密码表
  KeyExpansion(key, expandedKey );
  InvCipher(in, expandedKey ,out);
  /*
  for(int p=0;p<16;p++)
	printf("%x",out[p]);
  printf("-----");
  
  //BYTE space1[ 256 ];                                                            //AES加密用
  //expandedKey =space1;                                                           //至此space用来存贮密码表
  //KeyExpansion(key1, expandedKey );
  InvCipher(in1, expandedKey ,out1);
  
  for(int q=0;q<16;q++)
	printf("%x",out1[q]);
  printf("-----");
  
  //printf(name);
  */
 // return out1;
}

BYTE * use_aesDecrypt( BYTE *in,BYTE *key,BYTE *out)   
{
	  
  		BYTE data2[16];
		BYTE key2[16] = {0x20,0x37,0x62,0x39,0x31,0x31,0x31,0x37,0x63,0x30,0x30,0x32,0x31,0x38,0x33,0x33};
		BYTE databuffer2[16]={0x5A,0x29,0x1C,0x6E,0x41,0xC0,0xAE,0x3E,0x7D,0xD4,0xBA,0xC0,0x2F,0x69,0x3F,0xF6};
			//0x36,0xA8,0xBE,0xD7,0x09,0xFC,0x34,0xB2,0xF2,0x68,0x88,0xAF,0xD1,0xA7,0x3C,0xF8};
		aesDecrypt(in,key,out);
		for(int f =0;f<16;f++)
			printf("%x",out[f]);
		printf("\n");
		//BYTE out[16]={0xe2,0x03,0x05,0x4f,0x4b,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
		return out;
}

//******************************************************************************//对一个16字节块完成加密，参数buffer是加密缓存，chainBlock是要加密的块
void aesEncrypt( BYTE * in, BYTE *key,BYTE *out)                                //加密函数
{
  BYTE space[ 256 ];                                                            //AES加密用
  expandedKey = space;                                                          //至此space用来存贮密码表
  KeyExpansion(key, expandedKey );
  Cipher(in, expandedKey ,out);
}
//******************************************************************************//对一个16字节块完成加密，参数buffer是加密缓存，chainBlock是要加密的块
void delay(WORD count)
{
  while(count--);
}
//******************************************************************************//对一个16字节块完成加密，参数buffer是加密缓存，chainBlock是要加密的块
void TimeDelay(WORD time)                                                       //延时1ms子程序
{
  WORD i=0;
  for(i=0;i<time;i++)delay(1050);                                                      //内循环1ms
}
//******************************************************************************// 
BYTE BCD_code(BYTE data)                                                        //BCD编码
{ 
  BYTE temp;
  if(data>='0'&&data<='9')                                                      //如果0-9的情况
    temp=data-'0';
  if(data>='A'&&data<='F')                                                      //如果A-F的情况
    temp=data+10-'A';
  if(data>='a'&&data<='f')                                                      //如果a-f的情况
    temp=data+10-'a';
  return temp;
}

//******************************************************************************// 
BYTE CMX_Decode(BYTE receivedata)                                               //CMX解码子程序
{
  BYTE data=0;
  switch(receivedata&0x0F)                                                      //根据receivedata的低字节解码
  {
    case 0x00:data='D'; break;                                                  //如果是０的情况　　　　　　　　　　　　　　　　
    case 0x01:data='1'; break;                                                  //如果是１的情况
    case 0x02:data='2'; break;                                                  //如果是２的情况
    case 0x03:data='3'; break;                                                  //如果是３的情况
    case 0x04:data='4'; break;                                                  //如果是４的情况
    case 0x05:data='5'; break;                                                  //如果是５的情况
    case 0x06:data='6'; break;                                                  //如果是６的情况
    case 0x07:data='7'; break;                                                  //如果是７的情况
    case 0x08:data='8'; break;                                                  //如果是８的情况
    case 0x09:data='9'; break;                                                  //如果是９的情况
    case 0x0A:data='0'; break;                                                  //如果是１０的情况
    case 0x0B:data='*'; break;                                                  //如果是１１的情况
    case 0x0C:data='#'; break;                                                  //如果是１２的情况
    case 0x0D:data='A'; break;                                                  //如果是１３的情况
    case 0x0E:data='B'; break;                                                  //如果是１４的情况
    case 0x0F:data='C'; break;                                                  //如果是１５的情况
  }
  return data;                                                                  //返回data　　　　　　　　　　　　　　　　　
}

#include<stdio.h>
/*
void main(){

	 BYTE temp = 0;  
	//BYTE key1[16]={0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,                          //密钥
     //         0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f};
     BYTE buffer[16],keybuffer[16];
	 */
/*
	data[0]=0x01;                                                           
    data[1]=0x02;                                                           
    data[2]=0x05;                                                         
      data[3]='O';           
       data[4]='K';                                                         
       for(i=0;i<16;i++)
         data[i] = '1'; 
		 */
	   //BYTE data[16]={0x00,0x01,0x02,0x03,0x04,0x05,0x06,
	//	   0x07,0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f};
	/*
	 BYTE data[16]={0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f};
       CopyBytes( buffer,data,16 );  
	   for(int k =0;k<16;k++)
		printf("%x",buffer[k]);
	   printf("\n");
	   	BYTE key1[16]={0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f};
       CopyBytes( keybuffer,key1,16 );
	   for(int u =0;u<16;u++)
		printf("%x",keybuffer[u]);
	   printf("\n");
       aesEncrypt(buffer,keybuffer,data);
	   for(int j =0;j<16;j++)
			printf("%x",data[j]);
	    printf("\n");

	   	BYTE databuffer[16]={0x0a,0x94,0x0b,0xb5,0x41,0x6e,0xf0,0x45,0xf1,0xc3,0x94,0x58,0xc6,0x53,0xea,0x5a};
		aesDecrypt(databuffer,key1,data);
		for(int p =0;p<16;p++)
			printf("%x",data[p]);
		printf("\n");
}
*/