--
-- PostgreSQL database dump
--

\restrict gBzunmOd3Tcn012UrVkNe3k1grMvyNwaACn2EM7relDtfybWDRJlTRUBSLr0IAv

-- Dumped from database version 18.6
-- Dumped by pg_dump version 18.6

-- Started on 2026-11-08 21:29:49

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 16404)
-- Name: inventory; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.inventory (
    item_id bigint NOT NULL,
    item_name character varying(150) NOT NULL,
    quantity integer DEFAULT 0 NOT NULL,
    size character varying(50),
    gender character varying(50),
    color character varying(50),
    brand character varying(100),
    date_received date,
    donor character varying(150),
    acknowledged character varying(30),
    status character varying(50),
    estimated_value numeric(12,2),
    school character varying(150),
    notes text,
    CONSTRAINT inventory_quantity_check CHECK ((quantity >= 0))
);


ALTER TABLE public.inventory OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16403)
-- Name: inventory_item_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.inventory ALTER COLUMN item_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.inventory_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 4859 (class 2606 OID 16416)
-- Name: inventory inventory_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT inventory_pkey PRIMARY KEY (item_id);


-- Completed on 2026-11-08 21:29:49

--
-- PostgreSQL database dump complete
--

\unrestrict gBzunmOd3Tcn012UrVkNe3k1grMvyNwaACn2EM7relDtfybWDRJlTRUBSLr0IAv

