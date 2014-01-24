--------------------------------------------------------------------------------
OMX_Component.h

// FIXME: key data structure!
/** The OMX_HANDLETYPE structure defines the component handle.  The component 
 *  handle is used to access all of the component's public methods and also
 *  contains pointers to the component's private data area.  The component
 *  handle is initialized by the OMX core (with help from the component)
 *  during the process of loading the component.  After the component is
 *  successfully loaded, the application can safely access any of the
 *  component's public functions (although some may return an error because
 *  the state is inappropriate for the access).
 * 
 *  @ingroup comp
 */
typedef struct OMX_COMPONENTTYPE {
}

enum OMX_PORTDOMAINTYPE: 
    port type, audio, video, image...

struct OMX_PARAM_PORTDEFINITIONTYPE:   
    represent a port. port index, direct, buffer info, ...

// omx command types
typedef enum OMX_COMMANDTYPE
{
    OMX_CommandStateSet,    /**< Change the component state */
    OMX_CommandFlush,       /**< Flush the data queue(s) of a component */
    OMX_CommandPortDisable, /**< Disable a port on a component. */
    OMX_CommandPortEnable,  /**< Enable a port on a component. */
    OMX_CommandMarkBuffer,  /**< Mark a component/buffer for observation */
    OMX_CommandKhronosExtensions = 0x6F000000, /**< Reserved region for introducing Khronos Standard Extensions */ 
    OMX_CommandVendorStartUnused = 0x7F000000, /**< Reserved region for introducing Vendor Extensions */
    OMX_CommandMax = 0X7FFFFFFF
} OMX_COMMANDTYPE;


/** The OMX_INDEXTYPE enumeration is used to select a structure when either
 *  getting or setting parameters and/or configuration data.  Each entry in 
 *  this enumeration maps to an OMX specified structure.  When the 
 *  OMX_GetParameter, OMX_SetParameter, OMX_GetConfig or OMX_SetConfig methods
 *  are used, the second parameter will always be an entry from this enumeration
 *  and the third entry will be the structure shown in the comments for the entry.
 *  For example, if the application is initializing a cropping function, the 
 *  OMX_SetConfig command would have OMX_IndexConfigCommonInputCrop as the second parameter 
 *  and would send a pointer to an initialized OMX_RECTTYPE structure as the 
 *  third parameter.
 *  
 *  The enumeration entries named with the OMX_Config prefix are sent using
 *  the OMX_SetConfig command and the enumeration entries named with the
 *  OMX_PARAM_ prefix are sent using the OMX_SetParameter command.
 */
typedef enum OMX_INDEXTYPE {

    OMX_IndexComponentStartUnused = 0x01000000,
    OMX_IndexParamPriorityMgmt,             /**< reference: OMX_PRIORITYMGMTTYPE */
    OMX_IndexParamAudioInit,                /**< reference: OMX_PORT_PARAM_TYPE */
    OMX_IndexParamImageInit,                /**< reference: OMX_PORT_PARAM_TYPE */
    OMX_IndexParamVideoInit,                /**< reference: OMX_PORT_PARAM_TYPE */
    ...
}

/** The OMX_STATETYPE enumeration is used to indicate or change the component
 *  state.  This enumeration reflects the current state of the component when
 *  used with the OMX_GetState macro or becomes the parameter in a state change
 *  command when used with the OMX_SendCommand macro.
 *
 *  The component will be in the Loaded state after the component is initially
 *  loaded into memory.  In the Loaded state, the component is not allowed to
 *  allocate or hold resources other than to build it's internal parameter
 *  and configuration tables.  The application will send one or more
 *  SetParameters/GetParameters and SetConfig/GetConfig commands to the
 *  component and the component will record each of these parameter and
 *  configuration changes for use later.  When the application sends the
 *  Idle command, the component will acquire the resources needed for the
 *  specified configuration and will transition to the idle state if the
 *  allocation is successful.  If the component cannot successfully
 *  transition to the idle state for any reason, the state of the component
 *  shall be fully rolled back to the Loaded state (e.g. all allocated 
 *  resources shall be released).  When the component receives the command
 *  to go to the Executing state, it shall begin processing buffers by
 *  sending all input buffers it holds to the application.  While
 *  the component is in the Idle state, the application may also send the
 *  Pause command.  If the component receives the pause command while in the
 *  Idle state, the component shall send all input buffers it holds to the 
 *  application, but shall not begin processing buffers.  This will allow the
 *  application to prefill buffers.
 */
typedef enum OMX_STATETYPE
{
    OMX_StateInvalid,      /**< component has detected that it's internal data 
                             structures are corrupted to the point that
                             it cannot determine it's state properly */
    OMX_StateLoaded,      /**< component has been loaded but has not completed
                            initialization.  The OMX_SetParameter macro
                            and the OMX_GetParameter macro are the only 
                            valid macros allowed to be sent to the 
                            component in this state. */
    OMX_StateIdle,        /**< component initialization has been completed
                            successfully and the component is ready to
                            to start. */
    OMX_StateExecuting,   /**< component has accepted the start command and
                            is processing data (if data is available) */
    OMX_StatePause,       /**< component has received pause command */
    OMX_StateWaitForResources, /**< component is waiting for resources, either after 
                                 preemption or before it gets the resources requested.
                                 See specification for complete details. */
    ...
} OMX_STATETYPE;


/** The OMX_TUNNELSETUPTYPE structure is used to pass data from an output
    port to an input port as part the two ComponentTunnelRequest calls
    resulting from a OMX_SetupTunnel call from the IL Client. 
 */   
typedef struct OMX_TUNNELSETUPTYPE
{
    OMX_U32 nTunnelFlags;             /**< bit flags for tunneling */
    OMX_BUFFERSUPPLIERTYPE eSupplier; /**< supplier preference */
} OMX_TUNNELSETUPTYPE; 


/** The OMX_BUFFERSUPPLIERTYPE enumeration is used to dictate port supplier
    preference when tunneling between two ports.
 */
typedef enum OMX_BUFFERSUPPLIERTYPE
{
    OMX_BufferSupplyUnspecified = 0x0, /**< port supplying the buffers is unspecified,
                                         or don't care */
    OMX_BufferSupplyInput,             /**< input port supplies the buffers */
    OMX_BufferSupplyOutput,            /**< output port supplies the buffers */
    ...
} OMX_BUFFERSUPPLIERTYPE;


// represent a buffer

/** @ingroup buf */
typedef struct OMX_BUFFERHEADERTYPE
{
    OMX_U32 nSize;              /**< size of the structure in bytes */
    OMX_VERSIONTYPE nVersion;   /**< OMX specification version information */
    OMX_U8* pBuffer;            /**< Pointer to actual block of memory 
                                  that is acting as the buffer */
    OMX_U32 nAllocLen;          /**< size of the buffer allocated, in bytes */
    OMX_U32 nFilledLen;         /**< number of bytes currently in the 
                                  buffer */
    OMX_U32 nOffset;            /**< start offset of valid data in bytes from
                                  the start of the buffer */
    OMX_PTR pAppPrivate;        /**< pointer to any data the application
                                  wants to associate with this buffer */
    OMX_PTR pPlatformPrivate;   /**< pointer to any data the platform
                                  wants to associate with this buffer */ 
    OMX_PTR pInputPortPrivate;  /**< pointer to any data the input port
                                  wants to associate with this buffer */
    OMX_PTR pOutputPortPrivate; /**< pointer to any data the output port
                                  wants to associate with this buffer */
    OMX_HANDLETYPE hMarkTargetComponent; /**< The component that will generate a 
                                           mark event upon processing this buffer. */
    OMX_PTR pMarkData;          /**< Application specific data associated with 
                                  the mark sent on a mark event to disambiguate 
                                  this mark from others. */
    OMX_U32 nTickCount;         /**< Optional entry that the component and
                                  application can update with a tick count
                                  when they access the component.  This
                                  value should be in microseconds.  Since
                                  this is a value relative to an arbitrary
                                  starting point, this value cannot be used 
                                  to determine absolute time.  This is an
                                  optional entry and not all components
                                  will update it.*/
    OMX_TICKS nTimeStamp;          /**< Timestamp corresponding to the sample 
                                     starting at the first logical sample 
                                     boundary in the buffer. Timestamps of 
                                     successive samples within the buffer may
                                     be inferred by adding the duration of the 
                                     of the preceding buffer to the timestamp
                                     of the preceding buffer.*/
    OMX_U32     nFlags;           /**< buffer specific flags */
    OMX_U32 nOutputPortIndex;     /**< The index of the output port (if any) using 
                                    this buffer */
    OMX_U32 nInputPortIndex;      /**< The index of the input port (if any) using
                                    this buffer */
} OMX_BUFFERHEADERTYPE;


typedef enum OMX_EVENTTYPE
{
    OMX_EventCmdComplete,         /**< component has sucessfully completed a command */
    OMX_EventError,               /**< component has detected an error condition */
    OMX_EventMark,                /**< component has detected a buffer mark */
    OMX_EventPortSettingsChanged, /**< component is reported a port settings change */
    OMX_EventBufferFlag,          /**< component has detected an EOS */ 
    OMX_EventResourcesAcquired,   /**< component has been granted resources and is
                                    automatically starting the state change from
                                    OMX_StateWaitForResources to OMX_StateIdle. */
    OMX_EventComponentResumed,     /**< Component resumed due to reacquisition of resources */
    OMX_EventDynamicResourcesAvailable, /**< Component has acquired previously unavailable dynamic resources */
    OMX_EventPortFormatDetected,      /**< Component has detected a supported format. */
    OMX_EventKhronosExtensions = 0x6F000000, /**< Reserved region for introducing Khronos Standard Extensions */ 
    OMX_EventVendorStartUnused = 0x7F000000, /**< Reserved region for introducing Vendor Extensions */
    OMX_EventMax = 0x7FFFFFFF
} OMX_EVENTTYPE;



typedef struct OMX_CALLBACKTYPE
{
    /** The EventHandler method is used to notify the application when an
      event of interest occurs.  Events are defined in the OMX_EVENTTYPE
      enumeration.  Please see that enumeration for details of what will
      be returned for each type of event. Callbacks should not return
      an error to the component, so if an error occurs, the application 
      shall handle it internally.  This is a blocking call.

      The application should return from this call within 5 msec to avoid
      blocking the component for an excessively long period of time.

      @param hComponent
      handle of the component to access.  This is the component
      handle returned by the call to the GetHandle function.
      @param pAppData
      pointer to an application defined value that was provided in the 
      pAppData parameter to the OMX_GetHandle method for the component.
      This application defined value is provided so that the application 
      can have a component specific context when receiving the callback.
      @param eEvent
      Event that the component wants to notify the application about.
      @param nData1
      nData will be the OMX_ERRORTYPE for an error event and will be 
      an OMX_COMMANDTYPE for a command complete event and OMX_INDEXTYPE for a OMX_PortSettingsChanged event.
      @param nData2
      nData2 will hold further information related to the event. Can be OMX_STATETYPE for
      a OMX_CommandStateSet command or port index for a OMX_PortSettingsChanged event.
      Default value is 0 if not used. )
      @param pEventData
      Pointer to additional event-specific data (see spec for meaning).
     */

    OMX_ERRORTYPE (*EventHandler)(
            OMX_IN OMX_HANDLETYPE hComponent,
            OMX_IN OMX_PTR pAppData,
            OMX_IN OMX_EVENTTYPE eEvent,
            OMX_IN OMX_U32 nData1,
            OMX_IN OMX_U32 nData2,
            OMX_IN OMX_PTR pEventData);

    /** The EmptyBufferDone method is used to return emptied buffers from an
      input port back to the application for reuse.  This is a blocking call 
      so the application should not attempt to refill the buffers during this
      call, but should queue them and refill them in another thread.  There
      is no error return, so the application shall handle any errors generated
      internally.  

      The application should return from this call within 5 msec.

      @param hComponent
      handle of the component to access.  This is the component
      handle returned by the call to the GetHandle function.
      @param pAppData
      pointer to an application defined value that was provided in the 
      pAppData parameter to the OMX_GetHandle method for the component.
      This application defined value is provided so that the application 
      can have a component specific context when receiving the callback.
      @param pBuffer
      pointer to an OMX_BUFFERHEADERTYPE structure allocated with UseBuffer
      or AllocateBuffer indicating the buffer that was emptied.
      @ingroup buf
     */
    OMX_ERRORTYPE (*EmptyBufferDone)(
            OMX_IN OMX_HANDLETYPE hComponent,
            OMX_IN OMX_PTR pAppData,
            OMX_IN OMX_BUFFERHEADERTYPE* pBuffer);

    /** The FillBufferDone method is used to return filled buffers from an
      output port back to the application for emptying and then reuse.  
      This is a blocking call so the application should not attempt to 
      empty the buffers during this call, but should queue the buffers 
      and empty them in another thread.  There is no error return, so 
      the application shall handle any errors generated internally.  The 
      application shall also update the buffer header to indicate the
      number of bytes placed into the buffer.  

      The application should return from this call within 5 msec.

      @param hComponent
      handle of the component to access.  This is the component
      handle returned by the call to the GetHandle function.
      @param pAppData
      pointer to an application defined value that was provided in the 
      pAppData parameter to the OMX_GetHandle method for the component.
      This application defined value is provided so that the application 
      can have a component specific context when receiving the callback.
      @param pBuffer
      pointer to an OMX_BUFFERHEADERTYPE structure allocated with UseBuffer
      or AllocateBuffer indicating the buffer that was filled.
      @ingroup buf
     */
    OMX_ERRORTYPE (*FillBufferDone)(
            OMX_OUT OMX_HANDLETYPE hComponent,
            OMX_OUT OMX_PTR pAppData,
            OMX_OUT OMX_BUFFERHEADERTYPE* pBuffer);

} OMX_CALLBACKTYPE;







--------------------------------------------------------------------------------
OMX_Core.h


