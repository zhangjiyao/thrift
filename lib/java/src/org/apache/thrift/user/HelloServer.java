package org.apache.thrift.user;


import org.apache.thrift.*;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.RDMATAsyncClient;
import org.apache.thrift.async.RDMATAsyncClientManager;
import org.apache.thrift.async.RDMATAsyncMethodCall;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.protocol.*;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.server.RDMATServer;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.apache.thrift.transport.TNonblockingTransport;
import org.apache.thrift.transport.TTransportException;

import static org.apache.thrift.protocol.TMessageType.EXCEPTION;

public class HelloServer {

    public interface Iface {
        public String sayString(String param) throws TException;
    }

    public interface AsyncIface {
        public void sayString(String param, AsyncMethodCallback<String> resultHandler) throws TException;
    }

    public static class Client extends TServiceClient implements Iface {
        public static class Factory implements TServiceClientFactory<Client> {
            public Factory() {
            }

            public Client getClient(TProtocol prot) {
                return new Client(prot);
            }

            public Client getClient(TProtocol iprot, TProtocol oprot) {
                return new Client(iprot, oprot);
            }
        }

        public Client(TProtocol prot) {
            super(prot, prot);
        }

        public Client(TProtocol iprot, TProtocol oprot) {
            super(iprot, oprot);
        }

        public String sayString(String param) throws TException {
            send_sayString(param);
            return recv_sayString();
        }

        public void send_sayString(String param) throws TException {
            sayString_args args = new sayString_args();
            args.setParam(param);
            sendBase("sayString", args);
        }

        public String recv_sayString() {
            sayString_result result = new sayString_result();
            try {
                receiveBase(result, "sayString");
            } catch (TException e) {
                e.printStackTrace();
            }
            if (result.isSetSuccess()) {
                return result.success;
            }
            return null;
        }
    }

    public static class AsyncClient extends RDMATAsyncClient implements AsyncIface {

        public AsyncClient(TProtocolFactory protocolFactory, RDMATAsyncClientManager clientManager, TNonblockingTransport transport) {
            super(protocolFactory, clientManager, transport);
        }

        public void sayString(String param, AsyncMethodCallback<String> resultHandler) throws TException {
            sayString_call method_call = new sayString_call(param, resultHandler, this, ___protocolFactory, ___transport);
            ___manager.call(method_call);
        }

        public static class sayString_call extends RDMATAsyncMethodCall<String> {
            private String param;

            public sayString_call(String param, AsyncMethodCallback<String> resultHandler, RDMATAsyncClient client, TProtocolFactory protocolFactory, TNonblockingTransport transport) throws TException {
                super(client, protocolFactory, transport, resultHandler, false);
                this.param = param;
            }

            public void write_args(TProtocol prot) throws TException {
                prot.writeMessageBegin(new TMessage("sayString", TMessageType.CALL, 0));
                sayString_args args = new sayString_args();
                args.setParam(param);
                args.write(prot);
                prot.writeMessageEnd();
            }

            public String getResult() throws TException {
                TMemoryInputTransport memoryTransport = new TMemoryInputTransport(getFrameBuffer().array());
                TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
                return (new Client(prot)).recv_sayString();
            }
        }

    }


    public static class AsyncProcessor<I extends AsyncIface> extends TBaseAsyncProcessor<I> {
        private static final org.slf4j.Logger _LOGGER = org.slf4j.LoggerFactory.getLogger(AsyncProcessor.class.getName());

        public AsyncProcessor(I iface) {
            super(iface, getProcessMap(new java.util.HashMap<String, AsyncProcessFunction<I, ? extends TBase, ?>>()));
        }

        protected AsyncProcessor(I iface, java.util.Map<String, AsyncProcessFunction<I, ? extends TBase, ?>> processMap) {
            super(iface, getProcessMap(processMap));
        }

        private static <I extends AsyncIface> java.util.Map<String, AsyncProcessFunction<I, ? extends TBase, ?>> getProcessMap(java.util.Map<String, AsyncProcessFunction<I, ? extends TBase, ?>> processMap) {
            processMap.put("sayString", new sayString());
            return processMap;
        }

        public static class sayString<I extends AsyncIface> extends AsyncProcessFunction<I, sayString_args, String> {
            public sayString() {
                super("sayString");
            }

            public sayString_args getEmptyArgsInstance() {
                return new sayString_args();
            }

            public AsyncMethodCallback<String> getResultHandler(final RDMATServer.AsyncFrameBuffer fb, final int seqid) {
                final AsyncProcessFunction fcall = this;
                if (fcall == null)
                    System.out.println("fcall ==null");
                return new AsyncMethodCallback<String>() {
                    public void onComplete(String o) {
                        sayString_result result = new sayString_result();
                        System.out.println(o);
                        result.success = o;
                        try {
                            fcall.sendResponse(fb, result, TMessageType.REPLY, seqid);
                        } catch (TTransportException e) {
                            _LOGGER.error("TTransportException writing to internal frame buffer", e);
                        } catch (Exception e) {
                            _LOGGER.error("Exception writing to internal frame buffer", e);
                            onError(e);
                        }
                    }

                    public void onError(Exception e) {
                        byte msgType = TMessageType.REPLY;
                        TSerializable msg;
                        sayString_result result = new sayString_result();
                        if (e instanceof TTransportException) {
                            _LOGGER.error("TTransportException inside handler", e);
                            return;
                        } else if (e instanceof TApplicationException) {
                            _LOGGER.error("TApplicationException inside handler", e);
                            msgType = EXCEPTION;
                            msg = (TApplicationException) e;
                        } else {
                            _LOGGER.error("Exception inside handler", e);
                            msgType = EXCEPTION;
                            msg = new TApplicationException(TApplicationException.INTERNAL_ERROR, e.getMessage());
                        }
                        try {
                            fcall.sendResponse(fb, msg, msgType, seqid);
                        } catch (Exception ex) {
                            _LOGGER.error("Exception writing to internal frame buffer", ex);
                        }
                    }
                };
            }

            protected boolean isOneway() {
                return false;
            }

            public void start(I iface, sayString_args args, AsyncMethodCallback<String> resultHandler) throws TException {
                iface.sayString(args.param, resultHandler);
            }
        }

    }

    public static class sayString_args implements TBase<sayString_args, sayString_args._Fields>, java.io.Serializable, Cloneable, Comparable<sayString_args> {
        private static final TStruct STRUCT_DESC = new TStruct("sayString_args");

        private static final TField PARAM_FIELD_DESC = new TField("param", TType.STRING, (short) 1);

        private static final SchemeFactory STANDARD_SCHEME_FACTORY = new sayString_argsStandardSchemeFactory();
        private static final SchemeFactory TUPLE_SCHEME_FACTORY = new sayString_argsTupleSchemeFactory();

        public String param; // required

        /**
         * The set of fields this struct contains, along with convenience methods for finding and manipulating them.
         */
        public enum _Fields implements TFieldIdEnum {
            PARAM((short) 1, "param");

            private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

            static {
                for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            /**
             * Find the _Fields constant that matches fieldId, or null if its not found.
             */
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 1: // PARAM
                        return PARAM;
                    default:
                        return null;
                }
            }

            /**
             * Find the _Fields constant that matches fieldId, throwing an exception
             * if it is not found.
             */
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            /**
             * Find the _Fields constant that matches name, or null if its not found.
             */
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

        // isset id assignments
        public static final java.util.Map<_Fields, FieldMetaData> metaDataMap;

        static {
            java.util.Map<_Fields, FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.PARAM, new FieldMetaData("param", TFieldRequirementType.DEFAULT,
                    new FieldValueMetaData(TType.STRING)));
            metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
            FieldMetaData.addStructMetaDataMap(sayString_args.class, metaDataMap);
        }

        public sayString_args() {
        }

        public sayString_args(
                String param) {
            this();
            this.param = param;
        }

        /**
         * Performs a deep copy on <i>other</i>.
         */
        public sayString_args(sayString_args other) {
            if (other.isSetParam()) {
                this.param = other.param;
            }
        }

        public sayString_args deepCopy() {
            return new sayString_args(this);
        }


        public void clear() {
            this.param = null;
        }

        public String getParam() {
            return this.param;
        }

        public sayString_args setParam(String param) {
            this.param = param;
            return this;
        }

        public void unsetParam() {
            this.param = null;
        }

        /**
         * Returns true if field param is set (has been assigned a value) and false otherwise
         */
        public boolean isSetParam() {
            return this.param != null;
        }

        public void setParamIsSet(boolean value) {
            if (!value) {
                this.param = null;
            }
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case PARAM:
                    if (value == null) {
                        unsetParam();
                    } else {
                        setParam((String) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case PARAM:
                    return getParam();

            }
            throw new IllegalStateException();
        }

        /**
         * Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise
         */
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case PARAM:
                    return isSetParam();
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof sayString_args)
                return this.equals((sayString_args) that);
            return false;
        }

        public boolean equals(sayString_args that) {
            if (that == null)
                return false;
            if (this == that)
                return true;

            boolean this_present_param = true && this.isSetParam();
            boolean that_present_param = true && that.isSetParam();
            if (this_present_param || that_present_param) {
                if (!(this_present_param && that_present_param))
                    return false;
                if (!this.param.equals(that.param))
                    return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int hashCode = 1;

            hashCode = hashCode * 8191 + ((isSetParam()) ? 131071 : 524287);
            if (isSetParam())
                hashCode = hashCode * 8191 + param.hashCode();

            return hashCode;
        }


        public int compareTo(sayString_args other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;

            lastComparison = Boolean.valueOf(isSetParam()).compareTo(other.isSetParam());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetParam()) {
//        lastComparison = TBaseHelper.compareTo(this.param, other.param);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(TProtocol iprot) throws TException {
            scheme(iprot).read(iprot, this);
        }

        public void write(TProtocol oprot) throws TException {
            scheme(oprot).write(oprot, this);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("sayString_args(");
            boolean first = true;

            sb.append("param:");
            if (this.param == null) {
                sb.append("null");
            } else {
                sb.append(this.param);
            }
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws TException {
            // check for required fields
            // check for sub-struct validity
        }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new TCompactProtocol(new TIOStreamTransport(out)));
            } catch (TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                read(new TCompactProtocol(new TIOStreamTransport(in)));
            } catch (TException te) {
                throw new java.io.IOException(te);
            }
        }

        private static class sayString_argsStandardSchemeFactory implements SchemeFactory {
            public sayString_argsStandardScheme getScheme() {
                return new sayString_argsStandardScheme();
            }
        }

        private static class sayString_argsStandardScheme extends StandardScheme<sayString_args> {

            public void read(TProtocol iprot, sayString_args struct) throws TException {
                TField schemeField;
                iprot.readStructBegin();
                while (true) {
                    schemeField = iprot.readFieldBegin();
                    if (schemeField.type == TType.STOP) {
                        break;
                    }
                    switch (schemeField.id) {
                        case 1: // PARAM
                            if (schemeField.type == TType.STRING) {
                                struct.param = iprot.readString();
                                struct.setParamIsSet(true);
                            } else {
                                TProtocolUtil.skip(iprot, schemeField.type);
                            }
                            break;
                        default:
                            TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    iprot.readFieldEnd();
                }
                iprot.readStructEnd();

                // check for required fields of primitive type, which can't be checked in the validate method
                struct.validate();
            }

            public void write(TProtocol oprot, sayString_args struct) throws TException {
                struct.validate();

                oprot.writeStructBegin(STRUCT_DESC);
                if (struct.param != null) {
                    oprot.writeFieldBegin(PARAM_FIELD_DESC);
                    oprot.writeString(struct.param);
                    oprot.writeFieldEnd();
                }
                oprot.writeFieldStop();
                oprot.writeStructEnd();
            }

        }

        private static class sayString_argsTupleSchemeFactory implements SchemeFactory {
            public sayString_argsTupleScheme getScheme() {
                return new sayString_argsTupleScheme();
            }
        }

        private static class sayString_argsTupleScheme extends TupleScheme<sayString_args> {


            public void write(TProtocol prot, sayString_args struct) throws TException {
                TTupleProtocol oprot = (TTupleProtocol) prot;
                java.util.BitSet optionals = new java.util.BitSet();
                if (struct.isSetParam()) {
                    optionals.set(0);
                }
                oprot.writeBitSet(optionals, 1);
                if (struct.isSetParam()) {
                    oprot.writeString(struct.param);
                }
            }


            public void read(TProtocol prot, sayString_args struct) throws TException {
                TTupleProtocol iprot = (TTupleProtocol) prot;
                java.util.BitSet incoming = iprot.readBitSet(1);
                if (incoming.get(0)) {
                    struct.param = iprot.readString();
                    struct.setParamIsSet(true);
                }
            }
        }

        private static <S extends IScheme> S scheme(TProtocol proto) {
            return (StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
        }
    }

    public static class sayString_result implements TBase<sayString_result, sayString_result._Fields>, java.io.Serializable, Cloneable, Comparable<sayString_result> {
        private static final TStruct STRUCT_DESC = new TStruct("sayString_result");

        private static final TField SUCCESS_FIELD_DESC = new TField("success", TType.STRING, (short) 0);

        private static final SchemeFactory STANDARD_SCHEME_FACTORY = new sayString_resultStandardSchemeFactory();
        private static final SchemeFactory TUPLE_SCHEME_FACTORY = new sayString_resultTupleSchemeFactory();

        public String success; // required

        /**
         * The set of fields this struct contains, along with convenience methods for finding and manipulating them.
         */
        public enum _Fields implements TFieldIdEnum {
            SUCCESS((short) 0, "success");

            private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

            static {
                for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
                    byName.put(field.getFieldName(), field);
                }
            }

            /**
             * Find the _Fields constant that matches fieldId, or null if its not found.
             */
            public static _Fields findByThriftId(int fieldId) {
                switch (fieldId) {
                    case 0: // SUCCESS
                        return SUCCESS;
                    default:
                        return null;
                }
            }

            /**
             * Find the _Fields constant that matches fieldId, throwing an exception
             * if it is not found.
             */
            public static _Fields findByThriftIdOrThrow(int fieldId) {
                _Fields fields = findByThriftId(fieldId);
                if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
                return fields;
            }

            /**
             * Find the _Fields constant that matches name, or null if its not found.
             */
            public static _Fields findByName(String name) {
                return byName.get(name);
            }

            private final short _thriftId;
            private final String _fieldName;

            _Fields(short thriftId, String fieldName) {
                _thriftId = thriftId;
                _fieldName = fieldName;
            }

            public short getThriftFieldId() {
                return _thriftId;
            }

            public String getFieldName() {
                return _fieldName;
            }
        }

        // isset id assignments
        public static final java.util.Map<_Fields, FieldMetaData> metaDataMap;

        static {
            java.util.Map<_Fields, FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, FieldMetaData>(_Fields.class);
            tmpMap.put(_Fields.SUCCESS, new FieldMetaData("success", TFieldRequirementType.DEFAULT,
                    new FieldValueMetaData(TType.STRING)));
            metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
            FieldMetaData.addStructMetaDataMap(sayString_result.class, metaDataMap);
        }

        public sayString_result() {
        }

        public sayString_result(
                String success) {
            this();
            this.success = success;
        }

        /**
         * Performs a deep copy on <i>other</i>.
         */
        public sayString_result(sayString_result other) {
            if (other.isSetSuccess()) {
                this.success = other.success;
            }
        }

        public sayString_result deepCopy() {
            return new sayString_result(this);
        }


        public void clear() {
            this.success = null;
        }

        public String getSuccess() {
            return this.success;
        }

        public sayString_result setSuccess(String success) {
            this.success = success;
            return this;
        }

        public void unsetSuccess() {
            this.success = null;
        }

        /**
         * Returns true if field success is set (has been assigned a value) and false otherwise
         */
        public boolean isSetSuccess() {
            return this.success != null;
        }

        public void setSuccessIsSet(boolean value) {
            if (!value) {
                this.success = null;
            }
        }

        public void setFieldValue(_Fields field, Object value) {
            switch (field) {
                case SUCCESS:
                    if (value == null) {
                        unsetSuccess();
                    } else {
                        setSuccess((String) value);
                    }
                    break;

            }
        }

        public Object getFieldValue(_Fields field) {
            switch (field) {
                case SUCCESS:
                    return getSuccess();

            }
            throw new IllegalStateException();
        }

        /**
         * Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise
         */
        public boolean isSet(_Fields field) {
            if (field == null) {
                throw new IllegalArgumentException();
            }

            switch (field) {
                case SUCCESS:
                    return isSetSuccess();
            }
            throw new IllegalStateException();
        }

        @Override
        public boolean equals(Object that) {
            if (that == null)
                return false;
            if (that instanceof sayString_result)
                return this.equals((sayString_result) that);
            return false;
        }

        public boolean equals(sayString_result that) {
            if (that == null)
                return false;
            if (this == that)
                return true;

            boolean this_present_success = true && this.isSetSuccess();
            boolean that_present_success = true && that.isSetSuccess();
            if (this_present_success || that_present_success) {
                if (!(this_present_success && that_present_success))
                    return false;
                if (!this.success.equals(that.success))
                    return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int hashCode = 1;

            hashCode = hashCode * 8191 + ((isSetSuccess()) ? 131071 : 524287);
            if (isSetSuccess())
                hashCode = hashCode * 8191 + success.hashCode();

            return hashCode;
        }


        public int compareTo(sayString_result other) {
            if (!getClass().equals(other.getClass())) {
                return getClass().getName().compareTo(other.getClass().getName());
            }

            int lastComparison = 0;

            lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(other.isSetSuccess());
            if (lastComparison != 0) {
                return lastComparison;
            }
            if (isSetSuccess()) {
//        lastComparison = TBaseHelper.compareTo(this.success, other.success);
                if (lastComparison != 0) {
                    return lastComparison;
                }
            }
            return 0;
        }

        public _Fields fieldForId(int fieldId) {
            return _Fields.findByThriftId(fieldId);
        }

        public void read(TProtocol iprot) throws TException {
            scheme(iprot).read(iprot, this);
        }

        public void write(TProtocol oprot) throws TException {
            scheme(oprot).write(oprot, this);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("sayString_result(");
            boolean first = true;

            sb.append("success:");
            if (this.success == null) {
                sb.append("null");
            } else {
                sb.append(this.success);
            }
            first = false;
            sb.append(")");
            return sb.toString();
        }

        public void validate() throws TException {
            // check for required fields
            // check for sub-struct validity
        }

        private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
            try {
                write(new TCompactProtocol(new TIOStreamTransport(out)));
            } catch (TException te) {
                throw new java.io.IOException(te);
            }
        }

        private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
            try {
                read(new TCompactProtocol(new TIOStreamTransport(in)));
            } catch (TException te) {
                throw new java.io.IOException(te);
            }
        }

        private static class sayString_resultStandardSchemeFactory implements SchemeFactory {
            public sayString_resultStandardScheme getScheme() {
                return new sayString_resultStandardScheme();
            }
        }

        private static class sayString_resultStandardScheme extends StandardScheme<sayString_result> {

            public void read(TProtocol iprot, sayString_result struct) throws TException {
                TField schemeField;
                iprot.readStructBegin();
                while (true) {
                    schemeField = iprot.readFieldBegin();
                    if (schemeField.type == TType.STOP) {
                        break;
                    }
                    switch (schemeField.id) {
                        case 0: // SUCCESS
                            if (schemeField.type == TType.STRING) {
                                struct.success = iprot.readString();
                                struct.setSuccessIsSet(true);
                            } else {
                                TProtocolUtil.skip(iprot, schemeField.type);
                            }
                            break;
                        default:
                            TProtocolUtil.skip(iprot, schemeField.type);
                    }
                    iprot.readFieldEnd();
                }
                iprot.readStructEnd();

                // check for required fields of primitive type, which can't be checked in the validate method
                struct.validate();
            }

            public void write(TProtocol oprot, sayString_result struct) throws TException {
                struct.validate();

                oprot.writeStructBegin(STRUCT_DESC);
                if (struct.success != null) {
                    oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
                    oprot.writeString(struct.success);
                    oprot.writeFieldEnd();
                }
                oprot.writeFieldStop();
                oprot.writeStructEnd();
            }

        }

        private static class sayString_resultTupleSchemeFactory implements SchemeFactory {
            public sayString_resultTupleScheme getScheme() {
                return new sayString_resultTupleScheme();
            }
        }

        private static class sayString_resultTupleScheme extends TupleScheme<sayString_result> {


            public void write(TProtocol prot, sayString_result struct) throws TException {
                TTupleProtocol oprot = (TTupleProtocol) prot;
                java.util.BitSet optionals = new java.util.BitSet();
                if (struct.isSetSuccess()) {
                    optionals.set(0);
                }
                oprot.writeBitSet(optionals, 1);
                if (struct.isSetSuccess()) {
                    oprot.writeString(struct.success);
                }
            }


            public void read(TProtocol prot, sayString_result struct) throws TException {
                TTupleProtocol iprot = (TTupleProtocol) prot;
                java.util.BitSet incoming = iprot.readBitSet(1);
                if (incoming.get(0)) {
                    struct.success = iprot.readString();
                    struct.setSuccessIsSet(true);
                }
            }
        }

        private static <S extends IScheme> S scheme(TProtocol proto) {
            return (StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
        }
    }

}