/* struct to encapsulate one complete RDS group */
/* This structure is used internally to store data until a complete RDS
 * group was received and group id dependent decoding can be done.
 * It is also used to provide external access to uninterpreted RDS groups
 * when manual decoding is required (e.g. special ODA types) */
struct v4l2_rds_group {
	uint16_t pi;		/* Program Identification */
	char group_version;	/* group version ('A' / 'B') */
	uint8_t group_id;	/* group number (0..16) */

	/* uninterpreted data blocks for decoding (e.g. ODA) */
	uint8_t data_b_lsb;
	uint8_t data_c_msb;
	uint8_t data_c_lsb;
	uint8_t data_d_msb;
	uint8_t data_d_lsb;
};


