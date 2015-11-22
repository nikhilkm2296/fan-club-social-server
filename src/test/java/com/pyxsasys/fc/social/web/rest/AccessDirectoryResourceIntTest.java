package com.pyxsasys.fc.social.web.rest;

import com.pyxsasys.fc.social.Application;
import com.pyxsasys.fc.social.domain.AccessDirectory;
import com.pyxsasys.fc.social.repository.AccessDirectoryRepository;
import com.pyxsasys.fc.social.web.rest.dto.AccessDirectoryDTO;
import com.pyxsasys.fc.social.web.rest.mapper.AccessDirectoryMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pyxsasys.fc.social.domain.enumeration.FCSocialMemberTypeEnum;

/**
 * Test class for the AccessDirectoryResource REST controller.
 *
 * @see AccessDirectoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class AccessDirectoryResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_ACCESS_CODE = "AAAAA";
    private static final String UPDATED_ACCESS_CODE = "BBBBB";


private static final FCSocialMemberTypeEnum DEFAULT_MEMBER_TYPE = FCSocialMemberTypeEnum.admin;
    private static final FCSocialMemberTypeEnum UPDATED_MEMBER_TYPE = FCSocialMemberTypeEnum.member;

    @Inject
    private AccessDirectoryRepository accessDirectoryRepository;

    @Inject
    private AccessDirectoryMapper accessDirectoryMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restAccessDirectoryMockMvc;

    private AccessDirectory accessDirectory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AccessDirectoryResource accessDirectoryResource = new AccessDirectoryResource();
        ReflectionTestUtils.setField(accessDirectoryResource, "accessDirectoryRepository", accessDirectoryRepository);
        ReflectionTestUtils.setField(accessDirectoryResource, "accessDirectoryMapper", accessDirectoryMapper);
        this.restAccessDirectoryMockMvc = MockMvcBuilders.standaloneSetup(accessDirectoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        accessDirectoryRepository.deleteAll();
        accessDirectory = new AccessDirectory();
        accessDirectory.setEmail(DEFAULT_EMAIL);
        accessDirectory.setAccessCode(DEFAULT_ACCESS_CODE);
        accessDirectory.setMemberType(DEFAULT_MEMBER_TYPE);
    }

    @Test
    public void createAccessDirectory() throws Exception {
        int databaseSizeBeforeCreate = accessDirectoryRepository.findAll().size();

        // Create the AccessDirectory
        AccessDirectoryDTO accessDirectoryDTO = accessDirectoryMapper.accessDirectoryToAccessDirectoryDTO(accessDirectory);

        restAccessDirectoryMockMvc.perform(post("/api/accessDirectorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(accessDirectoryDTO)))
                .andExpect(status().isCreated());

        // Validate the AccessDirectory in the database
        List<AccessDirectory> accessDirectorys = accessDirectoryRepository.findAll();
        assertThat(accessDirectorys).hasSize(databaseSizeBeforeCreate + 1);
        AccessDirectory testAccessDirectory = accessDirectorys.get(accessDirectorys.size() - 1);
        assertThat(testAccessDirectory.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAccessDirectory.getAccessCode()).isEqualTo(DEFAULT_ACCESS_CODE);
        assertThat(testAccessDirectory.getMemberType()).isEqualTo(DEFAULT_MEMBER_TYPE);
    }

    @Test
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = accessDirectoryRepository.findAll().size();
        // set the field null
        accessDirectory.setEmail(null);

        // Create the AccessDirectory, which fails.
        AccessDirectoryDTO accessDirectoryDTO = accessDirectoryMapper.accessDirectoryToAccessDirectoryDTO(accessDirectory);

        restAccessDirectoryMockMvc.perform(post("/api/accessDirectorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(accessDirectoryDTO)))
                .andExpect(status().isBadRequest());

        List<AccessDirectory> accessDirectorys = accessDirectoryRepository.findAll();
        assertThat(accessDirectorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAccessCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = accessDirectoryRepository.findAll().size();
        // set the field null
        accessDirectory.setAccessCode(null);

        // Create the AccessDirectory, which fails.
        AccessDirectoryDTO accessDirectoryDTO = accessDirectoryMapper.accessDirectoryToAccessDirectoryDTO(accessDirectory);

        restAccessDirectoryMockMvc.perform(post("/api/accessDirectorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(accessDirectoryDTO)))
                .andExpect(status().isBadRequest());

        List<AccessDirectory> accessDirectorys = accessDirectoryRepository.findAll();
        assertThat(accessDirectorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMemberTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = accessDirectoryRepository.findAll().size();
        // set the field null
        accessDirectory.setMemberType(null);

        // Create the AccessDirectory, which fails.
        AccessDirectoryDTO accessDirectoryDTO = accessDirectoryMapper.accessDirectoryToAccessDirectoryDTO(accessDirectory);

        restAccessDirectoryMockMvc.perform(post("/api/accessDirectorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(accessDirectoryDTO)))
                .andExpect(status().isBadRequest());

        List<AccessDirectory> accessDirectorys = accessDirectoryRepository.findAll();
        assertThat(accessDirectorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAccessDirectorys() throws Exception {
        // Initialize the database
        accessDirectoryRepository.save(accessDirectory);

        // Get all the accessDirectorys
        restAccessDirectoryMockMvc.perform(get("/api/accessDirectorys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(accessDirectory.getId())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].accessCode").value(hasItem(DEFAULT_ACCESS_CODE.toString())))
                .andExpect(jsonPath("$.[*].memberType").value(hasItem(DEFAULT_MEMBER_TYPE.toString())));
    }

    @Test
    public void getAccessDirectory() throws Exception {
        // Initialize the database
        accessDirectoryRepository.save(accessDirectory);

        // Get the accessDirectory
        restAccessDirectoryMockMvc.perform(get("/api/accessDirectorys/{id}", accessDirectory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(accessDirectory.getId()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.accessCode").value(DEFAULT_ACCESS_CODE.toString()))
            .andExpect(jsonPath("$.memberType").value(DEFAULT_MEMBER_TYPE.toString()));
    }

    @Test
    public void getNonExistingAccessDirectory() throws Exception {
        // Get the accessDirectory
        restAccessDirectoryMockMvc.perform(get("/api/accessDirectorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateAccessDirectory() throws Exception {
        // Initialize the database
        accessDirectoryRepository.save(accessDirectory);

		int databaseSizeBeforeUpdate = accessDirectoryRepository.findAll().size();

        // Update the accessDirectory
        accessDirectory.setEmail(UPDATED_EMAIL);
        accessDirectory.setAccessCode(UPDATED_ACCESS_CODE);
        accessDirectory.setMemberType(UPDATED_MEMBER_TYPE);
        AccessDirectoryDTO accessDirectoryDTO = accessDirectoryMapper.accessDirectoryToAccessDirectoryDTO(accessDirectory);

        restAccessDirectoryMockMvc.perform(put("/api/accessDirectorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(accessDirectoryDTO)))
                .andExpect(status().isOk());

        // Validate the AccessDirectory in the database
        List<AccessDirectory> accessDirectorys = accessDirectoryRepository.findAll();
        assertThat(accessDirectorys).hasSize(databaseSizeBeforeUpdate);
        AccessDirectory testAccessDirectory = accessDirectorys.get(accessDirectorys.size() - 1);
        assertThat(testAccessDirectory.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAccessDirectory.getAccessCode()).isEqualTo(UPDATED_ACCESS_CODE);
        assertThat(testAccessDirectory.getMemberType()).isEqualTo(UPDATED_MEMBER_TYPE);
    }

    @Test
    public void deleteAccessDirectory() throws Exception {
        // Initialize the database
        accessDirectoryRepository.save(accessDirectory);

		int databaseSizeBeforeDelete = accessDirectoryRepository.findAll().size();

        // Get the accessDirectory
        restAccessDirectoryMockMvc.perform(delete("/api/accessDirectorys/{id}", accessDirectory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<AccessDirectory> accessDirectorys = accessDirectoryRepository.findAll();
        assertThat(accessDirectorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
